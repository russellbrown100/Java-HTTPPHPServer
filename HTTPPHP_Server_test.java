/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpphp_server_test;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Russell Brown
 */
public class HTTPPHP_Server_test {
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         try
        {
            // Source:  https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java/
            

               // System.out.println(fileData);
                                
                                
		ServerSocket server = new ServerSocket(80);
                
                
                while (true)
                {
                    Socket socket = server.accept();
                    
                    
                    new Thread(() -> 
                    {
                        
                    while (true)
                    {
                        
                        try
                        {
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());
                            PrintWriter out = new PrintWriter(socket.getOutputStream());

                            String input = in.readLine();
                            
                            if (input != null)
                            {
                            
                                
                                StringTokenizer parse = new StringTokenizer(input);
                                String method = parse.nextToken().toUpperCase();

                                if (method.equals("GET")) 
                                { // GET method so we return content
                                
                                    
                                    System.out.println("client accepted");
                                    
                                    //-----

                                    String cmd = "cmd /c start /b php \"C:\\Users\\Russell Brown\\Documents\\NetBeansProjects\\PHPResponse_test\\phpfile.php\"";

                                    Runtime runtime = Runtime.getRuntime();
                                    Process process = runtime.exec(cmd);

                                    InputStream is = process.getInputStream();
                                    InputStreamReader isr = new InputStreamReader(is);
                                    BufferedReader br = new BufferedReader(isr);
                                    
                                    String phpresponse = "";
                                    String line;

                                    while ((line = br.readLine()) != null) {
                                        phpresponse += line;
                                    }
                                    //---
            
                                
                                    String response = "<html><head></head><body>" + phpresponse + " </body></html>";

                                    int responseLength = response.length();

                                    // send HTTP Headers
                                    out.println("HTTP/1.1 200 OK");
                                    out.println("Server: Java HTTP Server from SSaurel : 1.0");
                                    out.println("Date: " + new Date());
                                    out.println("Content-type: " + "text/html");
                                    out.println("Content-length: " + responseLength);
                                    out.println(); 
                                    out.flush(); 

                                    dataOut.write(response.getBytes(), 0, responseLength);
                                    dataOut.flush();
                                
                                


                                }

                            }

                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                    }).start();

                    
                }
                    
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
     
        
        
    }
    
}
