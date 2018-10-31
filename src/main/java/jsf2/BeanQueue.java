package jsf2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import me.legrange.mikrotik.ApiConnection;
import model.Queue;
import util.DatosRouter;

@ManagedBean(name="BeanQueue")
@SessionScoped
public class BeanQueue {

	List<Queue> queues = new ArrayList<>();
	Queue queue = new Queue();
	
	private int vsubida;
	private int vbajada;
	private int maxvsubida;
	private int maxvbajada;
	public void CargarQueues() throws Exception
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
			try 
			{
				con.login(DatosRouter.user, DatosRouter.password);
				List<Map<String, String>> queues =con.execute("/queue/simple/print where parent!=none and parent!=master and comment!=x");
				List<Queue> lst = new ArrayList<>();
				Queue queue;
					for (Map<String, String> map : queues) 
					{
						 queue= new Queue();
						queue.setId(map.get(".id"));
						queue.setComment(map.get("comment"));
						queue.setMaxlimit(map.get("max-limit"));
						queue.setLimitAt(map.get("limit-at"));
						queue.setName(map.get("name"));
						queue.setTarget(map.get("target"));
						queue.setTime(map.get("time"));
						lst.add(queue);
//						System.out.println(map);
					}
				this.queues=lst;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"ERROR: No se pudo cargar las colas",null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,e.toString(),null));
			}
			finally 
			{
				con.close();
			}
	}
	
	
	public void CrearQueue()throws Exception
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
			try 
			{
				vsubida=vsubida*1000;
				vbajada=vbajada*1000;
				maxvbajada=maxvbajada*1000;
				maxvsubida=maxvsubida*1000;
				queue.setLimitAt(vsubida+"/"+vbajada);
				queue.setMaxlimit(maxvsubida+"/"+maxvbajada);
				con.login(DatosRouter.user, DatosRouter.password);
				String cmd ="/queue/simple/add name="+queue.getName()+" target="+queue.getTarget()+" limit-at="+queue.getLimitAt()+" max-limit="+queue.getMaxlimit();
	//			System.out.println(cmd+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				con.execute(cmd);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OK: La cola para "+queue.getName()+" fue registrada exitosamente",null));

			}
			catch (Exception e) 
			{
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR: No se pudo registrar la cola "+e.toString(),null));
			}
			finally 
			{
				con.close();
			}
	}
	
	
	public void editarQueue() throws Exception
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
		try {
				if(!(maxvsubida <=20 || maxvbajada<=20 || vbajada<=20 || vsubida<=20))
				{
					queue.setMaxlimit(maxvsubida*1000+"/"+maxvbajada*1000);
					queue.setLimitAt(vsubida*1000+"/"+vbajada*1000);
		//			System.out.println(queue.getMaxlimit() +"  max limit");
		//			System.out.println(queue.getLimitAt()+" limit at");
					con.login(DatosRouter.user, DatosRouter.password);
					String cmd = "/queue/simple/set .id="+queue.getId()+" name="+queue.getName()+" target="+queue.getTarget()+" limit-at="+queue.getLimitAt()+" max-limit="+queue.getMaxlimit()+" ";
		//			System.err.println(cmd);
					con.execute(cmd);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OK: Los cambios fueron registrados exitosamente!s ",null));
				} 
				else 
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR: El valor minimo para el campo de velocidades es 20",null));
				}
			}
			catch (Exception e) 
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: No se pudo guardar los cambios "+e.toString(),null));	
				e.printStackTrace();
			}
			finally 
			{
				con.close();
				queue = new Queue();
				maxvbajada=0;
				maxvsubida=0;
				vsubida=0;
				vbajada=0;
			}
		}

	public void selectedQueue(Queue x) throws Exception
	{
		String velocidad = x.getLimitAt();
		String[] split = velocidad.split("/");
		vsubida=Integer.parseInt(split[0])/1000;
		vbajada=Integer.parseInt(split[1])/1000;
		String maxvelocidad = x.getMaxlimit();
		String[] maxsplit = maxvelocidad.split("/");
		maxvsubida=Integer.parseInt(maxsplit[0])/1000;
		maxvbajada=Integer.parseInt(maxsplit[1])/1000;
		queue=x;
		RequestContext.getCurrentInstance().update("imgForm:imgDlg");
		RequestContext.getCurrentInstance().execute("PF('dlg').show()");
	}
	
	public void CortarServicio(Queue x)throws Exception
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
			try 
			{
				con.login(DatosRouter.user, DatosRouter.password);
				con.execute("/queue/simple/set .id="+x.getId()+" limit-at=20/20 max-limit=20/20 ");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OK: El servicio de "+x.getName()+" fué cortado exitosamente.",null));
			} 
			catch (Exception e) 
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: no se pudo cortar el servicio "+e.toString(),null));
				e.printStackTrace();
			}
			finally 
			{
				con.close();
			}
	}
	
	
	public List<Queue> getQueues() {
		return queues;
	}

	
	public void setQueues(List<Queue> queues) {
		this.queues = queues;
	}





	public Queue getQueue() {
		return queue;
	}



	public void setQueue(Queue queue) {
		this.queue = queue;
	}



	public int getVsubida() {
		return vsubida;
	}



	public void setVsubida(int vsubida) {
		this.vsubida = vsubida;
	}



	public int getVbajada() {
		return vbajada;
	}



	public void setVbajada(int vbajada) {
		this.vbajada = vbajada;
	}



	public int getMaxvsubida() {
		return maxvsubida;
	}



	public void setMaxvsubida(int maxvsubida) {
		this.maxvsubida = maxvsubida;
	}



	public int getMaxvbajada() {
		return maxvbajada;
	}



	public void setMaxvbajada(int maxvbajada) {
		this.maxvbajada = maxvbajada;
	}
	
}
