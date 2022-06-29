import os

main = "javac -d ./bin ./main/Window.java"
entity = "javac -d ./bin ./entity/*.java"
item = "javac -d ./bin ./items/*.java"

os.system(main)
os.system(entity)
os.system(item)