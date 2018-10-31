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
import me.legrange.mikrotik.MikrotikApiException;
import model.HotspotUser;
import util.DatosRouter;


@ManagedBean(name = "BeanTest")
@SessionScoped
public class Beantest {
	//este sirve para registrar y editar
	HotspotUser hotspotUser = new HotspotUser();
	List<HotspotUser> hotspotUsers = null;
	HotspotUser selectedUser = new HotspotUser();
	
	
	public List<String> cargarPefilesHotspot() throws Exception
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
		try {
			
			con.login(DatosRouter.user, DatosRouter.password);
		
			List<Map<String , String>> map = con.execute("/ip/hotspot/user/profile/print");
			List<String> lst = new ArrayList<>();
			for (Map<String, String> map2 : map) 
			{
				lst.add(map2.get("name"));
			}
			return lst;
			} 
			catch (Exception e) 
			{
				return null;
				
			}
			finally {
				con.close();
			}
		
	}
	

public void seleccionarUsuario(HotspotUser x)
{
		selectedUser = x;
		RequestContext.getCurrentInstance().update("imgForm:imgDlg");
		RequestContext.getCurrentInstance().execute("PF('dlg').show()");
}


	
	public void EditarTicket(HotspotUser x) throws Exception
	{
		ApiConnection con = ApiConnection.connect("192.10.11.250");
		try {
			con.login("dscorp", "997627737");
			String command="/ip/hotspot/user/set .id="+selectedUser.getId()+" password="+selectedUser.getPassword()+" limit-uptime="+selectedUser.getLimitUptime()+" profile="+selectedUser.getProfile();
			con.execute(command);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Ok: El usuario fue actualizado correctamente",null));
			}	 
		catch (Exception e) 
			{
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error: No se pudo editar el usuario",null));
			}
			finally 
			{
				con.close();
			}
	}
	
	
	public void hotspotUsers() throws Exception
	{
		ApiConnection con = ApiConnection.connect("192.10.11.250");
		try 
		{
			con.login("dscorp", "997627737");
			List<Map<String, String>> usuarios=con.execute("/ip/hotspot/user/print");
			List<HotspotUser> hotspotUsers = new ArrayList<>();
			HotspotUser hu ;
				for (Map<String, String> map : usuarios) 
				{
					hu= new HotspotUser();
					hu.setId(map.get(".id"));
					hu.setName(map.get("name"));
					hu.setPassword(map.get("password"));
					String time = map.get("limit-uptime");
					if(time!=null)
					{
						time=time.substring(0, time.length()-1);
					}
					hu.setLimitUptime(time);
					hu.setProfile(map.get("profile"));
					hotspotUsers.add(hu);
				}
			this.hotspotUsers=hotspotUsers;
		}
		catch (Exception e) 
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Ocurrio un error al intentar cargar los usuarios",null));
			e.printStackTrace();
		}
		finally 
		{
			con.close();
		}
	}
	
	
	

	public void registrarUsuarioHotspot() throws MikrotikApiException 
	{
		ApiConnection con = ApiConnection.connect(DatosRouter.ip);
			try 
			{
				con.login(DatosRouter.user, DatosRouter.password);
				con.execute("/ip/hotspot/user/add name='" + hotspotUser.getName() + "' limit-uptime='"
						+ Integer.parseInt(hotspotUser.getLimitUptime()) * 60 + "' profile='" + hotspotUser.getProfile()
						+ "' password='" + hotspotUser.getPassword() + "'");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("OK!!! El usuario fue registrado exitosamente "));
				hotspotUser = new HotspotUser();
			}
			catch (Exception e) 
			{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!!! No se pudo registrar el usuario ", null));
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, e.toString(), null));
				e.printStackTrace();
			}
			finally 
			{
				con.close();
			}
	}
	
	public HotspotUser getHotspotUser() {
		return hotspotUser;
	}

	public void setHotspotUser(HotspotUser hotspotUser) {
		this.hotspotUser = hotspotUser;
	}


	public List<HotspotUser> getHotspotUsers() {
		return hotspotUsers;
	}


	public void setHotspotUsers(List<HotspotUser> hotspotUsers) {
		this.hotspotUsers = hotspotUsers;
	}


	public HotspotUser getSelectedUser() {
		return selectedUser;
	}


	public void setSelectedUser(HotspotUser selectedUser) {
		this.selectedUser = selectedUser;
	}

}
