import os

main = "javac -d ./bin ./main/Window.java"
entity = "javac -d ./bin ./entity/*.java"
item = "javac -d ./bin ./items/*.java"
pickup = "javac -d ./bin ./pickup/*.java"

os.system(main)
os.system(entity)
os.system(item)
os.system(pickup)