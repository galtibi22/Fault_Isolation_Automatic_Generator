from glob import glob
import json
import re
import sys
import time
import argparse
import os
import requests
import subprocess
from docx import Document
import common_functions as cf


#import win32com.client as win32
#from win32com.client import constants



#############################################################################################
#############################################################################################
###################################                    ######################################
###################################    Main program    ######################################
###################################                    ######################################
#############################################################################################
#############################################################################################

removeCharacters = ['\n','\t','\u200e']
FI_Label = True
FI_Descriptoin = ""
FI_Num = 0

parser = argparse.ArgumentParser()
parser.add_argument("source")
parser.add_argument("result")
args = parser.parse_args()
path = glob(args.source, recursive=True)
# document = Document(args.source)


# path = glob('C:\\Users\\eden.SPIDERSERVICES\\Desktop\\docx\\test\\DOC-Left-Wagon.doc', recursive=True)


if(path[0].endswith('docx')):
    print([path[0]])
    document = Document(path[0])
else:
    if (path[0].endswith('doc')):
       # if (os.name == 'posix'):
            print("use save_as_docx_mac")
            pathUrl=[cf.save_as_docx_mac(path[0])]
            f=open(pathUrl[0])
            f.close()
            document = Document(pathUrl)
        #else:
         #   print("use save_as_docx_win")
          #  document = Document(save_as_docx_win(path[0]))


FI_Array = []

# Main text info to object
FI_Main_Info = {}
FI_Main_Info['n'] = str(FI_Num)
FI_Num+=1
FI_Main_HTML_Obj ={}
FI_Main_HTML_Data = []
FI_Main_HTML_Data_Obj = {}

for para in document.paragraphs:
    if (FI_Label == True):
        FI_Main_HTML_Data_Obj['htmlType'] = 'fiTitle'
        FI_Main_HTML_Data_Obj['txt'] = para.text
        FI_Label = False
        FI_Main_HTML_Data.append(FI_Main_HTML_Data_Obj)
        FI_Main_HTML_Data_Obj = {}
    else:
        FI_Descriptoin+=para.text + "\n"

FI_Main_HTML_Data_Obj['htmlType'] = 'fiStpDsc'
FI_Main_HTML_Data_Obj['txt'] = FI_Descriptoin
FI_Main_HTML_Data.append(FI_Main_HTML_Data_Obj)

FI_Main_HTML_Obj['htmlData'] = FI_Main_HTML_Data
FI_Main_Info['htmlObj'] = FI_Main_HTML_Obj
FI_Array.append(FI_Main_Info)

tables = document.tables

# print(len(tables[0].rows[0].cells))
for table in tables:
    for row in table.rows:
        FI_row = []
        for cell in row.cells:
            for paragraph in cell.paragraphs:
                if not cf.checkYesNo(paragraph.text):
                    # print(paragraph.text)
                    FI_row.append(paragraph.text)
                    if len(FI_row) == 3:
                        des=FI_row[0]
                        newNumberObj = {}
                        newNumberObj['n'] = str(FI_Num)
                        if(('(' not in  FI_row[0]) and (')' not in  FI_row[0])):
                            # Step
                            newNumberObj['Y'] = {'to' : cf.removeCharsFunc(FI_row[1]), 'typ' : '0'}
                            newNumberObj['N'] = {'to' : cf.removeCharsFunc(FI_row[2]), 'typ' : '0'}
                            newNumberObj['htmlObj'] = cf.fiStepDescriptionQuestion(FI_row[0])
                        else:
                            # Task
                            newNumberObj['Y'] = cf.fiTaskYes(FI_row[0], FI_row[1], FI_row[2])
                            newNumberObj['N'] = { 'typ' : '4' }
                            newNumberObj['htmlObj'] = cf.fiTaskDescriptionQuestion(FI_row[0])
                        FI_Array.append(newNumberObj)
                        FI_Num+=1


###### Default end FI numbers ######
FI_Array.append({ "n": str(FI_Num), "htmlObj": { "htmlData": [ { "htmlType": "fiNegEnd" } ] }, "N": { "typ": "4" }, "Y": { "typ": "4" } })
FI_Num+=1
FI_Array.append({ "n": str(FI_Num), "htmlObj": { "htmlData": [ { "htmlType": "fiPosEnd" } ] }, "N": { "typ": "4" }, "Y": { "typ": "4" } })


f= open(args.result,"w+")
jsonrequest = "{\"PG\":"+json.dumps(FI_Array, indent=4, sort_keys=True)+"}"
f.write(jsonrequest)
print(jsonrequest)

cf.post_api_server(glob(args.result, recursive=True), jsonrequest)

