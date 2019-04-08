import json
import re
import sys
import argparse
from docx import Document
import common_functions as cf
import os

#############################################################################################
#############################################################################################
###################################                    ######################################
###################################    Main program    ######################################
###################################                    ######################################
#############################################################################################
#############################################################################################

FI_Label = True
FI_Num = 0

FI_Txt_Header = {}
FI_Txt_Header_Exist = False

# parser = argparse.ArgumentParser()
# parser.add_argument("file")
# args = parser.parse_args()
# document = Document(args.file)

path=cf.init()
pathUrl=cf.generate_fi_doc_path(path[0])
document = Document(pathUrl)

FI_Array_List = []
FI_Array = []

tables = document.tables
print(len(tables[0].rows[0].cells))

for table in tables:
    for row in table.rows:
        FI_row = []
        for cell in row.cells:
            for paragraph in cell.paragraphs:
                FI_row.append(paragraph.text)

                if(len(FI_row) == 8):

                    if not FI_Txt_Header_Exist:
                        for i in range(0,8):
                            FI_Txt_Header[i] = FI_row[i]
                        FI_Txt_Header_Exist = True

                    else:
                        # Main text info to object (Failure No., Tested Unit, Severity, Platform)
                        FI_Num=0
                        newNumberObj = {}
                        newNumberObj['n'] = str(FI_Num)
                        FI_Num+=1
                        newNumberObj['htmlObj'] = cf.fiMainDescription(FI_Txt_Header, FI_row)
                        FI_Array.append(newNumberObj)
                        Number_Of_Actions = 0

                        for i in range(4, 8):
                            if FI_row[i] != "":
                                Number_Of_Actions +=1

                        for i in range(4, 8):
                            if FI_row[i] != "":
                                newNumberObj = {}
                                newNumberObj['n'] = str(FI_Num)
                                if(i == 4):
                                    # Step
                                    newNumberObj['Y'] = {'to' : str(Number_Of_Actions+1), 'typ' : '0'}
                                    newNumberObj['N'] = {'to' : str(FI_Num+1), 'typ' : '0'}
                                else:
                                    # Task
                                    newNumberObj['Y'] = cf.fiTaskYes2(FI_row[i], str(Number_Of_Actions+1), str(FI_Num+1))
                                    newNumberObj['N'] = { 'typ' : '4' }
                                newNumberObj['htmlObj'] = cf.fiStepDescriptionQuestion(FI_Txt_Header[i] + ": " + FI_row[i])
                                FI_Array.append(newNumberObj)
                                FI_Num+=1


                        ###### Default end FI numbers ######
                        FI_Array.append({"n": str(FI_Num), "htmlObj": {"htmlData": [{"htmlType": "fiNegEnd"}]},
                                         "N": {"typ": "4"}, "Y": {"typ": "4"}})
                        FI_Num += 1
                        FI_Array.append({"n": str(FI_Num), "htmlObj": {"htmlData": [{"htmlType": "fiPosEnd"}]},
                                         "N": {"typ": "4"}, "Y": {"typ": "4"}})
                        FI={"PG":FI_Array}
                        FI_Array_List.append(FI)
                        FI_Array = []


os.remove(pathUrl)
cf.post_api_server(cf.args.result, FI_Array_List)
#print(json.dumps(FI_Array_List, indent=4, sort_keys=True))