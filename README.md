# java-signalr
java连接signalrde的Demo

src/main/java/fbox 是连接服务器相关的代码。
以上部分可复用，可以复制到目标工程调用。

src/main/java/下的3个文件是Demo程序相关的
src/main/java/FBoxSignalRConnection.java 为接收推送数据的回调类。
src/main/java/Global.java 存放了一些全局参数。

此项目可按maven或gradle工程导入，如按gradle工程导入，运行assemble任务即可编译工程，jar输出到build/libs下，如按maven工程导入，运行package任务可编译输出到target下

外部依赖库见pom.xml 或 build.gradle