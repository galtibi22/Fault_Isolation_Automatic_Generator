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
from shutil import copyfile

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
def fiMainDescription(Main_Rows, Des_Rows, FI_row):

    FI_Main_HTML_Data = []
    FI_Main_HTML_Data_Obj = {}


    # Main title
    FI_Main_HTML_Obj = {}
    FI_Main_HTML_Data_Obj['htmlType'] = 'fiTitle'
    FI_Descriptoin = ""
    for key, value in Main_Rows.items():
        FI_Descriptoin += Main_Rows[key] + ": " + FI_row[key] + " "
    FI_Main_HTML_Data_Obj['txt'] = FI_Descriptoin
    FI_Main_HTML_Data.append(FI_Main_HTML_Data_Obj)


    # Description title
    FI_Main_HTML_Data_Obj = {}
    FI_Main_HTML_Data_Obj['htmlType'] = 'fiStpDsc'
    FI_Descriptoin = ""
    for key, value in Des_Rows.items():
        FI_Descriptoin += Des_Rows[key] + ": " + FI_row[key] + "\n"
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
    yesObj['msg'] = "1"
    yesObj['msgIx'] = "0"

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
    yesObj['msg'] = "1"
    yesObj['msgIx'] = "0"

    return yesObj




def save_as_docx_win(path):
    import win32com.client as win32
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
    SOFFICE_PATH='/Users/gal.tibi/afekaProjects/Fault_Isolation_Automatic_Generator/Contents/MacOS/soffice'
    #SOFFICE_PATH="C:\Program Files\LibreOffice\program\soffice.exe"
    outdir=os.path.dirname(os.path.abspath(__file__))
    print("outdir",outdir)
    subprocess.call([SOFFICE_PATH, '--headless', '--convert-to', 'docx','--outdir', outdir, path])
    f = open(path)
    name=os.path.basename(f.name).replace("doc","docx")
    f.close()
    os.remove(path)
    newPath=outdir+"/"+name
    #newPath=path.replace("doc","docx")
    #copyfile(name,newPath)
    #os.remove(name)
    print("finish to convert doc to docx source path=",path,"result path=",newPath)
    return newPath



def post_api_server(jsondata):
    # defining the api-endpoint
    API_ENDPOINT = "http://127.0.0.1:8080/api/figenerator/new/"+sys.argv[2]+"/"+sys.argv[3]
    headers = { 'content-type' : 'application/json', 'Authorization' : 'Basic ZmlnZW5lcmF0b3I6QWExMjM0NTY='}
    print("send fi","to",API_ENDPOINT,"with headers",headers,"data",jsondata)
    r=requests.post(API_ENDPOINT,data=json.dumps(jsondata),headers = headers)
    print(r)


def generate_fi_doc_path(path):
    pathUrl=""
    if(path.endswith('docx')):
        pathUrl=path
    else:
        if (path.endswith('doc')):
            if (os.name == 'posix'):
                print("use save_as_docx_mac")
                pathUrl=save_as_docx_mac(path)
            else:
                print("use save_as_docx_win")
                pathUrl=save_as_docx_win(path)
    return pathUrl


def init():
    print("Start fi_generator client with the next params",sys.argv)
#    global args
 #   parser = argparse.ArgumentParser()
 #   parser.add_argument("source")
 #   parser.add_argument("result")
 #   parser.add_argument("fiDocId")
 #   print("args",sys.argv)
 #   args = parser.parse_args()
 #   path = glob(args.source, recursive=True)
 #   return path