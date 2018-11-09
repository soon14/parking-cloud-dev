#/usr/bin/env python
#-*- coding=utf-8 -*-

import os

rootPath = os.path.abspath('./')
createTableArr = []
alterTableArr = []
joinSqlFile = os.path.join(rootPath, 'final.sql')

def getFile(path = rootPath):
    list = os.listdir(path)

    for i in range(0, len(list)):
        filePath = os.path.join(path, list[i])

        if (filePath.find('initial_data.sql') > -1):
            continue

        if (os.path.isfile(filePath)):
            if (filePath.endswith('.sql')):
                if (filePath.lower().find('create') > -1) :
                    createTableArr.append(filePath)
                else:
                    alterTableArr.append(filePath)
        else:
            getFile(filePath)

if __name__ == '__main__':
    getFile()

    with open(joinSqlFile, 'w+') as final:
        final.write("BEGIN;\n")
        for file in createTableArr:
            with open(file, 'r') as content:
                final.write("\n")
                final.write(content.read())
        final.write("COMMIT;\n")

        final.write("BEGIN;\n")
        for file in alterTableArr:
            with open(file, 'r') as content:
                final.write("\n")
                final.write(content.read())
        final.write("COMMIT;\n")

        final.write("BEGIN;\n")
        with open(os.path.join(rootPath, 'initial_data.sql'), 'r') as init:
            final.write("\n\n\n")
            final.write(init.read())
        final.write("\nCOMMIT;")