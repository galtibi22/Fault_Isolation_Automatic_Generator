import json
import re
import sys
import argparse
from docx import Document

# Method return to Step description and question from string
def fiStepDescriptionQuestion(str):
    htmlDataObj = {}
    htmlDataObjAraay = []

    arrDesQue = str.split('.')

    if('?' not in str):
        objDes = {'htmlType':'fiStpDsc', 'txt' : str}
        htmlDataObjAraay.append(objDes)
    else:
        newDes = ""
        for description in arrDesQue[:-1]:
            newDes += description+"."
        objDes = {'htmlType':'fiStpDsc', 'txt' : newDes}
        htmlDataObjAraay.append(objDes)
        objQue = {'fiStpQst':'fiStpDsc', 'txt' : arrDesQue[-1]}
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