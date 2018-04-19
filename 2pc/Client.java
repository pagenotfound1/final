
import java.io.*;
import java.net.*;

public class Client implements Runnable{
    static Socket clientSocket = null;
    static PrintStream os = null;
    static DataInputStream is = null;
    static BufferedReader input = null;
    static boolean closed = false;


    public static void main(String[] args)
    {
     int port_number=8989;
     String host="localhost";

    try {
            clientSocket = new Socket(host, port_number);
            input = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
            } catch (Exception e) {
            System.out.println("Exception occurred : "+e.getMessage());
                }

        if (clientSocket != null && os != null && is != null)
        {
           try
               {
                    new Thread(new Client()).start();
       
                      while (!closed)
                     {
                       os.println(input.readLine());
                     }

         os.close();
         is.close();
         clientSocket.close();

              } catch (IOException e)
                  {
                System.err.println("IOException:  " + e);
                  }
       }
  }          
   
    public void run()
    {       
    String response;
    try
                {
        while ((response = is.readLine()) != null)
                        {
                     System.out.println("\n"+response);
if (response.equalsIgnoreCase("GLOBAL_COMMIT")==true || response.equalsIgnoreCase("GLOBAL_ABORT")==true )
                                  {
                                     break;
                                  }
            }
                   closed=true;
    } catch (IOException e)
                       {
             System.err.println("IOException:  " + e);
           }
    }
}

