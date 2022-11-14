package web;

import dao.CredentialsValidator;
import dao.DaoFactory;
import io.jooby.*;
import io.jooby.json.GsonModule;

import java.nio.file.Paths;
import java.util.Set;

public class Server extends Jooby {


	public Server() {
		setServerOptions(new ServerOptions().setPort(8085));

		mount(new StaticAssetModule());

		install(new GsonModule());

		install(new BasicAccessAuth(DaoFactory.getCustomerDAO(), Set.of("/api/.*"), Set.of("/api/register")));
		
		mount(new CustomerModule(DaoFactory.getCustomerDAO()));
		
		mount(new ProductModule(DaoFactory.getProductDAO()));

		mount(new SaleModule(DaoFactory.getSaleDAO(), DaoFactory.getProductDAO()));
	}

	public static void main(String[] args) {
		System.out.println("\nStarting Server.");
		new Server().start();
	}

}
