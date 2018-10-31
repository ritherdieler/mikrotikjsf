package jsf2;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import me.legrange.mikrotik.ResultListener;
import util.DatosRouter;

@ManagedBean(name="BeanMonitor")
@ViewScoped
    


public class BeanMonitor extends Thread {

	private int ping=0;
	private int ping2=0;
	private int ping3=0;
	private int ping4=0;




//	public void Monitor2() throws Exception
//	{
//		
//		ApiConnection con=ApiConnection.connect(DatosRouter.ip);
//		con.login(DatosRouter.user, DatosRouter.password);
//		while (true) {
//			Monitor(con);
//		}
//	}
		
	

	public void Monitor() throws Exception
	{

		ApiConnection con=ApiConnection.connect(DatosRouter.ip);
		con.login(DatosRouter.user, DatosRouter.password);		
		try 
		{
			int a =1;
			while (a == 1) {

				String tag = con.execute("/ping address= 192.168.100.21", new ResultListener() {
					public void receive(Map<String, String> result) {
						try {
							ping = Integer.parseInt(result.get("time").replace("ms", ""));

						}
						catch (Exception e)
						{
							ping=-1;
						}
						System.out.println(ping);
					}

					public void error(MikrotikApiException e) {
		               System.out.println("An error occurred: " + e.getMessage());
					}

					public void completed() {
		                System.out.println("Asynchronous command has finished");
					}
				}
		  );


				String tag2 = con.execute("/ping address= 192.168.100.22", new ResultListener() {
							public void receive(Map<String, String> result) {
								try {
									ping2 = Integer.parseInt(result.get("time").replace("ms", ""));

								}
								catch (Exception e)
								{
									ping2=-1;
								}
								System.out.println(ping);
							}

							public void error(MikrotikApiException e) {
								System.out.println("An error occurred: " + e.getMessage());
							}

							public void completed() {
								System.out.println("Asynchronous command has finished");
							}
						}
				);

				String tag3 = con.execute("/ping address= 192.168.100.23", new ResultListener() {
							public void receive(Map<String, String> result) {
								try {
									ping3 = Integer.parseInt(result.get("time").replace("ms", ""));
								}
								catch (Exception e)
								{
									ping3=-1;
								}

								System.out.println(ping);
							}

							public void error(MikrotikApiException e) {
								System.out.println("An error occurred: " + e.getMessage());
							}

							public void completed() {
								System.out.println("Asynchronous command has finished");
							}
						}
				);

				String tag4 = con.execute("/ping address= 192.168.100.24", new ResultListener() {
							public void receive(Map<String, String> result) {
								try {
									ping4 = Integer.parseInt(result.get("time").replace("ms", ""));
								}
								catch (Exception e){
								ping4=-1;
								}

								System.out.println(ping);
							}

							public void error(MikrotikApiException e) {
								System.out.println("An error occurred: " + e.getMessage());
							}

							public void completed() {
								System.out.println("Asynchronous command has finished");
							}
						}
				);

	
	Thread.sleep(1000);
	con.cancel(tag);
				con.cancel(tag2);
				con.cancel(tag3);
				con.cancel(tag4);
			}
			
	
					 
		} 
		catch (Exception e) 
		{
		 e.printStackTrace();
		}
	
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public int getPing2() {
		return ping2;
	}

	public void setPing2(int ping2) {
		this.ping2 = ping2;
	}

	public int getPing3() {
		return ping3;
	}

	public void setPing3(int ping3) {
		this.ping3 = ping3;
	}

	public int getPing4() {
		return ping4;
	}

	public void setPing4(int ping4) {
		this.ping4 = ping4;
	}
}
	
	
