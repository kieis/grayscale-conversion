<h1 align="center">Grayscale Conversion</h1>

<h4 align="center">Grayscale conversions made with Java using socket to redistribute images in equal portions to clients.</h4>

## Instalation
### Clone
```bash
# Clone repository with git:
git clone https://github.com/kieis/grayscale-conversion
```
### Importing with Eclipse
```bash
File -> Import -> File System -> Select the cloned project directory.
```

## Running
```bash
Package: br.com.sd.p2p
- Open -> SpaceServer.Java and modify "PATH_FILE" to your current directory.
- Run -> SpaceTimer.Java
- You have 10 seconds to init as many clients as you want, each of them will receive an equal portion of the image for conversion.
- To change 10 seconds interval, go to SpacetTimer.Java and changes "int interval = 10000".
- Run -> SpaceP2P.java, every time you start one before the server ends it will be a new client.
```

## Output/Preview
```bash
>> Server Started
Tue May 31 16:29:43 BRT 2022
Client connected: 1, IP: 127.0.0.1, Port: 57851
Client connected: 2, IP: 127.0.0.1, Port: 57852
Tue May 31 16:29:53 BRT 2022

>> Image Queue Size: 2
Sending image to client: 1
Sending image to client: 2
Server Received An Object [br.com.sd.pdi.GrayScaleConversion@1541beaf] from: Socket[addr=/127.0.0.1,port=57851,localport=45233]
Server Received An Object [br.com.sd.pdi.GrayScaleConversion@3ba99df3] from: Socket[addr=/127.0.0.1,port=57852,localport=45233]

>> Merging Images
D:\Eclipse\SD\eclipse-workspace\p2d-image-grayscale\images\mergedImg.jpg
```
### Original Image
![App Screenshot](https://i.imgur.com/2aCjihQ.jpg)
### Client 1
![App Screenshot](https://i.imgur.com/3Td6xOC.jpg)
### Client 2
![App Screenshot](https://i.imgur.com/Go6WaVc.jpg)
### Output Merged by Server
![App Screenshot](https://i.imgur.com/nsCxefN.jpg)
