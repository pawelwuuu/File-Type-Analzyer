# File-Type-Analzyer
File type analyzer is an app that gives opportunity to check the real file extension via file bytes. For example if there is a file with PDF extension, and it's filename extension has been modified to other, File type analyzer can help determining what file it actually is. It is handy to use this programme while running server that receives files from other computers. For example without checking file extension by file signature, somebody could send a virus that would be accepted by server because it's filename extension is correct. Programme supports multhithreading to speed up computing processes.

## Prepare to use
Place folder with files which have to be analyzed in folder containing application jar. Also place a file with list of files signatures to provide a way to determine the file type.
### File containing signatures
File containing signatures should look as below:
```code
1;"%PDF-";"PDF document"
2;"pmview";"PCP pmview config"
4;"PK";"Zip archive"
5;"vnd.oasis.opendocument.presentation";"OpenDocument presentation"
6;"W.o.r.d";"MS Office Word 2003"
6;"P.o.w.e.r.P.o.i";"MS Office PowerPoint 2003"
7;"word/_rels";"MS Office Word 2007+"
7;"ppt/_rels";"MS Office PowerPoint 2007+"
7;"xl/_rels";"MS Office Excel 2007+"
8;"-----BEGIN\ CERTIFICATE-----";"PEM certificate"
9;"ftypjp2";"ISO Media JPEG 2000"
9;"ftypiso2";"ISO Media MP4 Base Media v2"
```
Semicolon should be placed at start of line and between signature pattern and name of file extension. File signature pattern is a special string of characters that specify file extension. You can check some file signatures [here](https://en.wikipedia.org/wiki/List_of_file_signatures). File signatures must be in ISO 8859-1 form, for example if file signature is hex: 52 61 72 21 convert it to ASCII chars to get "Rar!" signature. Name of file extension will be printed next to filename of any determined by analyzer type. 

### Folder with files
Folder with files to check must be in the same place as jar. It can contain various file types.

## How to run it
Open terminal in place with jar and use this command pattern:
```bash
java -jar [jar_name] [folder_with_files] [file_containing_signatures]
```
For example:
```bash
java -jar "File type analyzer.jar" test_files patterns.db
```
Exemplary result:
```code
mkfds.pdf: Rar file
tape3.mp4: Unknown file type
```

## Download
Download jar [here](https://github.com/pawelwuuu/File-Type-Analzyer/releases/download/release/File.type.analyzer.jar)
