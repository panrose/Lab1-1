package guiex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GraphViz {
  private String runPath = "";                //文件的路径
  private String dotPath = "";
  private String runOrder = "";
  private String dotCodeFile = "graph.dot";
  private String resultGra = "graph";
  private StringBuilder graph = new StringBuilder();     //可变的字符串生成器
  public static final String[] color = {"red", "chartreuse", "blue", "gold",
    "darkgoldenrod", "fuchsia", "hotpink", "cadetblue", "green", "cyan",
    "yellow"};
  Runtime runtime = Runtime.getRuntime();
  //获取当前Runtime运行时对象的引用
  //Runtime使得程序能与运行的环境相连接

  /**
  * @author Song Hang
  * @author LuoWei
  * @versionVersion 1.00
  * @versionVersion 2.00
  */
  public void run() {
    File file = new File(runPath);
    file.mkdirs();
    //建立多级目录的文件夹
    writeGraphToFile(graph.toString(), runPath);
    creatOrder();
    try {
      runtime.exec("cmd /c " + runOrder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author Song Hang
   * @author LuoWei
   * @versionVersion 1.00
   * @versionVersion 2.00
   */
  public void creatOrder() {         //合成文件的一个路径
    runOrder += dotPath + " ";
    runOrder += runPath;
    runOrder += "\\" + dotCodeFile + " ";
    runOrder += "-T jpg ";
    runOrder += "-o ";
    runOrder += runPath;
    runOrder += "\\" + resultGra + ".jpg";
    //System.out.println(dotCodeFile);
    // System.out.println(runOrder);
  }

  /**
   * @author Song Hang
   * @author LuoWei
   * @versionVersion 1.00
   * @versionVersion 2.00
   */
  public void writeGraphToFile(String dotcode, String filename) {  //将图写进文件里
    try {
      File file = new File(filename + "\\" + dotCodeFile);//dot的编码文件
      if (!file.exists()) {
        file.createNewFile();
      }
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(dotcode.getBytes());
      fos.close();
    } catch (java.io.IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**  

   * @author Song Hang

   * @author LuoWei

   * @versionVersion 1.00

   * @versionVersion 2.00

   */  
  public GraphViz(String dotPath) {
    File f = new File("");
    try {
      this.runPath = f.getCanonicalPath();   //将符号完全解析的路径
    } catch (IOException e) {
      // TODO 自动生成的 catch 块
      e.printStackTrace();
    }
    this.dotPath = dotPath;
  }

  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  public void add(String line) {
    graph.append("\t" + line);
  }

  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  public void addln(String line) {
    graph.append("\t" + line + "\n");
  }

  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  
  public void addln(String line, int label) {
    graph.append(
        "\t" + line + "[label=" + String.valueOf(label) + "]\n");
  }
  
  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  public void addln() {
    graph.append('\n');
  }

  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  public void start_graph() {
    graph.append("digraph G {\n");
  }
    
  /**  

  * @author Song Hang

  * @author LuoWei

  * @versionVersion 1.00

  * @versionVersion 2.00

  */  
  public void setColorForPath(Stack<String> pathStack, String color,
      String dotName) {
    
    // 将待标记的每条边加入Set中
    Set<String> pathSet = new HashSet<String>();
    Stack<String> tmpStack = (Stack<String>) pathStack.clone();
    String str1 = "";
    String str2 = tmpStack.peek();
    tmpStack.pop();
    while (!tmpStack.isEmpty()) {
      str1 = tmpStack.peek();
      tmpStack.pop();
      pathSet.add("\"" + str1 + "\"->\"" + str2 + "\"");
      str2 = str1;
    }

    String targetFile = runPath + "\\" + dotName;
    File outpuFile = new File(targetFile);
    if (!outpuFile.exists()) {
      try {
        Files.copy(new File(runPath + "\\graph.dot").toPath(),
            outpuFile.toPath());
      } catch (IOException e) {
        // TODO 自动生成的 catch 块
        e.printStackTrace();
      }     
    }

    int pos = 0;
    String sourceFileName = runPath + "\\" + dotName;
    String tmpStr = "";
    File sourceFile = new File(sourceFileName);
    FileReader souceFileReader = null;

    //prepare readStream
    try {
      souceFileReader = new FileReader(sourceFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    BufferedReader sourceBufferedReader = new BufferedReader(souceFileReader);
    
    //prepare writeStream
    outpuFile = new File(runPath + "\\tmp.dot");
    try {
      outpuFile.createNewFile();
    } catch (IOException e2) {
      // TODO 自动生成的 catch 块
      e2.printStackTrace();
    }
    FileOutputStream fout = null;
    OutputStreamWriter outWriter = null;
    try {
      fout = new FileOutputStream(outpuFile);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    try {
      outWriter = new OutputStreamWriter(fout, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      // TODO 自动生成的 catch 块
      e1.printStackTrace();
    }  
    BufferedWriter bufWrite = new BufferedWriter(outWriter); 

    //add Head
    try {
      bufWrite.write("digraph G {\n");
    } catch (IOException e1) {
      // TODO 自动生成的 catch 块
      e1.printStackTrace();
    }
       
    //read one by one (line)
    try {
      while ((tmpStr = sourceBufferedReader.readLine()) != null) {
        if ((pos = tmpStr.indexOf("[")) < 0) {
          continue;
        }
        String edge = tmpStr.substring(tmpStr.indexOf("\t") + 1, pos);
        // 当前的边在待标记的路径上
        if (pathSet.contains(edge)) {
          StringBuffer tmpBuffer = new StringBuffer(tmpStr);
          pos = tmpStr.indexOf("]");
          // 已经包含在某条路径中，置为虚线
          if (tmpStr.indexOf("color") >= 0) {
            tmpBuffer.insert(pos,
                " style=tapered penwidth=4 arrowtail=normal");
          } else {
            tmpBuffer.insert(pos, " color=" + color);
          }
          tmpStr = tmpBuffer.toString();
        }
        tmpStr += "\n";
        bufWrite.write(tmpStr);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    // add tail && close stream
    try {
      bufWrite.write("}");
      bufWrite.close();
      fout.close();
      sourceBufferedReader.close();
      souceFileReader.close();
      sourceFile.delete();
    } catch (IOException e1) {
      // TODO 自动生成的 catch 块
      e1.printStackTrace();
    }
    sourceFile.delete();
    outpuFile.renameTo(sourceFile);
  }

  /**      
  * @author Song Hang
  * @author LuoWei
  * @versionVersion 1.00
  * @versionVersion 2.00
  */
  public void end_graph() {
    graph.append("}");
  }

  /**  
  * @author Song Hang
  * @author LuoWei
  * @versionVersion 1.00
  * @versionVersion 2.00
  */
  public void clearTmpDotFile(String dotName) {
    String fileNameString = runPath + "\\" + dotName;
    File tmpFile = new File(fileNameString);
    if (tmpFile.exists()) {
      tmpFile.delete();
    }
  }

  /**  
  * @author Song Hang
  * @author LuoWei
  * @versionVersion 1.00
  * @versionVersion 2.00
  */
  public void runAfterSetColor(String newJpgName, String dotName) {
    //create new Jpg file
    resultGra = newJpgName;
    dotCodeFile = dotName;
    creatOrder();
    try {
      runtime.exec(runOrder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
