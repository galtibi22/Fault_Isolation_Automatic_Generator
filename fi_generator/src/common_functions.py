from glob import glob
import json
import re
import sys
import argparse
import os
import requests
import subprocess
import time
from docx import Document


# Method check if string has Yes/No
def checkYesNo(str):
    if (str == "Yes" or str == "yes" or str == "No" or str == "no"):
        return True
    return False



# Method return to Step description and question from string
def fiStepDescriptionQuestion(str):
    htmlDataObj = {}
    htmlDataObjAraay = []

    arrDesQue = str.split('.')

    if('?' not in str):
        objDes = {'htmlType':'fiStpPrc', 'txt' : removeCharsFunc(str)}
        htmlDataObjAraay.append(objDes)
    else:
        newDes = ""
        for description in arrDesQue[:-1]:
            newDes += description+"."
        objDes = {'htmlType':'fiStpPrc', 'txt' : removeCharsFunc(newDes)}
        htmlDataObjAraay.append(objDes)
        objQue = {'htmlType':'fiStpQst', 'txt' : removeCharsFunc(arrDesQue[-1])}
        htmlDataObjAraay.append(objQue)

    htmlDataObj['htmlData'] = htmlDataObjAraay
    return htmlDataObj



# Method return description about FI flow
def fiMainDescription(Header_Name, Header_Description):

    FI_Descriptoin = ""
    FI_Main_HTML_Data = []
    FI_Main_HTML_Data_Obj = {}
    FI_Main_HTML_Obj = {}

    for i in range(1,4):
        FI_Descriptoin += Header_Name[i] + ": " + Header_Description[i] + "\n"

    FI_Main_HTML_Data_Obj['htmlType'] = 'fiTitle'
    FI_Main_HTML_Data_Obj['txt'] = Header_Name[0] + ": " + Header_Description[0]
    FI_Main_HTML_Data.append(FI_Main_HTML_Data_Obj)

    FI_Main_HTML_Data_Obj = {}
    FI_Main_HTML_Data_Obj['htmlType'] = 'fiStpDes'
    FI_Main_HTML_Data_Obj['txt'] = FI_Descriptoin
    FI_Main_HTML_Data.append(FI_Main_HTML_Data_Obj)

    FI_Main_HTML_Obj['htmlData'] = FI_Main_HTML_Data

    return FI_Main_HTML_Obj



# Method return remove characters
def removeCharsFunc(str):
    removeCharacters = ['\n','\t','\u200e']
    for rchar in removeCharacters:
        str = str.replace(rchar,'')
    return removeRegexFunc(str)



# Method return remove start characters with regex
def removeRegexFunc(str):
    return re.sub(r'(^ |^[0-9]{2}[A-Z])', '', str)



# Method return to Task description and question from string
def fiTaskDescriptionQuestion(str):
    htmlDataObj = {}
    htmlDataObjAraay = []

    arrDesQue = str.split('.')

    if('?' not in str):
        objDes = {'htmlType':'fiStpPrc', 'txt' : removeCharsFunc(str)}
        htmlDataObjAraay.append(objDes)
    else:
        newDes = ""
        for description in arrDesQue[:-1]:
            newDes += description+"."
        objDes = {'htmlType':'fiStpPrc', 'txt' : removeCharsFunc(newDes)}
        htmlDataObjAraay.append(objDes)

    htmlDataObj['htmlData'] = htmlDataObjAraay
    return htmlDataObj


# Method return Task Object to 'Y' (Yes option only)
def fiTaskYes(str, yesOption, noOption):
    yesObj = {}
    arrDesQue = str.split('.')
    newDes = ""

    if('?' not in str):
        newDes = str
    else:
        yesObj['msgRtIx'] = removeCharsFunc(arrDesQue[-1])
        for description in arrDesQue[:-1]:
            newDes += description+"."

    yesObj['to'] = removeCharsFunc(newDes[newDes.find("(")+1:newDes.find(")")])
    yesObj['rtN'] = removeCharsFunc(noOption)
    yesObj['msgRt'] = "1"
    yesObj['typ'] = "1"
    yesObj['tskNm'] = removeCharsFunc(newDes[:newDes.find("(")])
    yesObj['rtY'] = removeCharsFunc(yesOption)

    return yesObj


# Method return Task Object to 'Y' (Yes option only)
def fiTaskYes2(str, yesOption, noOption):
    yesObj = {}

    yesObj['to'] = removeCharsFunc(str)
    yesObj['rtN'] = removeCharsFunc(noOption)
    yesObj['msgRt'] = "1"
    yesObj['typ'] = "1"
    yesObj['msgRtIx'] = "0"
    yesObj['tskNm'] = removeCharsFunc(str)
    yesObj['rtY'] = removeCharsFunc(yesOption)

    return yesObj




def save_as_docx_win(path):
    # Opening MS Word
    word = win32.gencache.EnsureDispatch('Word.Application')
    doc = word.Documents.Open(path)
    doc.Activate ()

    # Rename path with .docx
    new_file_abs = os.path.abspath(path)
    new_file_abs = re.sub(r'\.\w+$', '.docx', new_file_abs)

    # Save and Close
    word.ActiveDocument.SaveAs(
        new_file_abs, FileFormat=constants.wdFormatXMLDocument
    )
    doc.Close(False)

    return new_file_abs


def save_as_docx_mac(path):
    #print("path",path)
    #SOFFICE_PATH='../../Office.app/Contents/MacOS/soffice'
    SOFFICE_PATH="C:\Program Files\LibreOffice\program\soffice.exe"
    subprocess.call([SOFFICE_PATH, '--headless', '--convert-to', 'docx', path])
    f = open(path)
    return os.path.basename(f.name).replace("doc","docx")



def post_api_server(uri, jsondata):
    # defining the api-endpoint
    API_ENDPOINT = "http://127.0.0.1/api/figenerator/new/"

    headers = {
        'content-type' : "application/json",
        'Authorization' : "Basic ZmlnZW5lcmF0b3I6QWExMjM0NTY="
    }
    r = requests.post(API_ENDPOINT+uri, data = jsondata, headers = headers)