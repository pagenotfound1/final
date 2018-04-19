
import java.io.*;
import java.net.*;
import java.util.*;

public class Server
 {
  boolean closed=false,inputFromAll=false;
  List<clientThread> t;          
  List<String> data; 

 Server()
 {
   t= new ArrayList<clientThread>();          
   data= new ArrayList<String>();
 }

  public static void main(String args[])
  {
      Socket clientSocket = null;
      ServerSocket serverSocket = null;
      int port_number=8989;

      Server s=new Server();
 
      try {
        serverSocket = new ServerSocket(port_number);
        }
        catch (IOException e)
        {System.out.println(e);}
   
    while(!s.closed)
                {
        try {
        clientSocket = serverSocket.accept();
                                clientThread th=new clientThread(s,clientSocket);
                                (s.t).add(th);
        System.out.println("\nTotal clients are : "+(s.t).size());
        (s.data).add("NOT_SENT");
        th.start();
               }
               catch (IOException e) {}
    }

        try
       {
         serverSocket.close();
       }catch(Exception e1){}
   
}
}

 class clientThread extends Thread
 {
   
 DataInputStream is = null;
 String line;
 String destClient="";
 String name;
 PrintStream os = null;
 Socket clientSocket = null;      
 String clientIdentity;
 Server s;

   public clientThread(Server s,Socket clientSocket)
    {
       this.clientSocket=clientSocket;
       this.s=s;
    }


   public void run()
    {
    try
                {
           is = new DataInputStream(clientSocket.getInputStream());
           os = new PrintStream(clientSocket.getOutputStream());
           os.println("Enter your name.");
           name = is.readLine();
                       clientIdentity=name;
os.println("Welcome "+name+" to this 2 Phase commit protocol \n");
os.println("VOTE_REQUEST:   Please enter COMMIT or ABORT to proceed : ");
        
        
        
        
         for(int i=0; i<(s.t).size(); i++)
           {
                if((s.t).get(i)!=this)
                                    {
                          ((s.t).get(i)).os.println("---A new user "+name+" entered in  Vote phase --");
                                     }
            }

         while (true)
         {
                     line = is.readLine();

                
                //
                 if(line.equalsIgnoreCase("ABORT"))
                 {
                             System.out.println("\nFrom '"+clientIdentity+"' : ABORT\n\nSince aborted we will not wait for inputs from other clients.");
                             System.out.println("\nAborted....");
                 for(int i=0; i<(s.t).size(); i++)
                 {
                               ((s.t).get(i)).os.println("GLOBAL_ABORT");
                               ((s.t).get(i)).os.close();
                               ((s.t).get(i)).is.close();
                              }

                        break;
                  }
			//
             	 if(line.equalsIgnoreCase("COMMIT"))
              	{
                   System.out.println("\nFrom '"+clientIdentity+"' : COMMIT");
                    if((s.t).contains(this))
                   {
                      (s.data).set((s.t).indexOf(this), "COMMIT");
                       for(int j=0;j<(s.data).size();j++)
                       {
                              if(!(((s.data).get(j)).equalsIgnoreCase("NOT_SENT")))
                                 {
                                    s.inputFromAll=true;
                                    continue;
        					 }
         				  else
       				 {
          					 s.inputFromAll=false;
                                  	 System.out.println("\nWaiting for inputs from other clients.");
           						break;
      		  			}
         			 }         			 
         			 

                      if(s.inputFromAll)
                         {
                            System.out.println("\n\nCommited....");
            
                            for(int i=0; i<(s.t).size(); i++)
                			 {
                                  ((s.t).get(i)).os.println("GLOBAL_COMMIT");
                                //  ((s.t).get(i)).os.close();
                                //  ((s.t).get(i)).is.close();
                               }
                            break;
                        }
                       
                       }//if t.contains
                         
                    }//commit

                 }//while
                 
                s.closed=true;
             clientSocket.close();

       }    catch(IOException e){};
    }
}
