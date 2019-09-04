package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

import app.AppContext;

import tools.ConnectionTools;
import tools.GridPanel;

import tools.MutableBoolean;
import toolsJSON.CreateEditClientJson;
import toolsJSON.CreateEventForClient;
import toolsJSON.CreateEventFromJSON;
import toolsJSON.CreateJSONClient;
import toolsJSON.CreateJSONEvents;
import toolsJSON.EditEventFromJson;
import toolsJSON.ReadNameClientJSON;
import toolsJSON.UpdateClientStatusJSON;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private AppContext context;
	private MutableBoolean stop;

	private String jsonEventsForClient;

	public MainFrame() {
		super();
		SetupGui();
	}

	private void SetupGui() {
		setTitle("Helden Server");

		context = new AppContext(ConnectionTools.openConnection("pbd", "pbdlab"));
		stop = new MutableBoolean(false);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		btnStop.setActionCommand("STOP");

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		btnStart.setActionCommand("START");

		GridPanel panel = new GridPanel(0, 0, 0, 0);
		panel.addObject(0, 0, 1.0, 0.0, 1, 1, btnStart);
		panel.addObject(1, 0, 1.0, 0.0, 1, 1, btnStop);

		setContentPane(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equalsIgnoreCase("START")) {

			stop.setValue(false);
			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket(9090);

			} catch (IOException ex) {
				System.out.println("Can't setup server on this port number. ");
			}

			Socket socket = null;

			InputStream in = null;
			OutputStream out = null;

			boolean run = true;
			while (run) {
				try {
					socket = serverSocket.accept();

				} catch (IOException ex) {
					System.out.println("Can't accept client connection. ");
				}

				if (socket != null) {

					System.out.println("innnn");
					byte[] data = new byte[16 * 1024];
					try {
						in = socket.getInputStream();
					} catch (IOException ex) {
						System.out.println("Can't get socket input stream. ");
					}

					try {
						in = socket.getInputStream();
						int count = in.read(data);
					}

					catch (IOException e1) {

						e1.printStackTrace();
					}

					String receviedCommand = new String(data);

					String addEventComparator = "adauga";
					String readClientsComparator = "clienti";
					String readEventsComparator = "events";
					String editEventComparator = "edit";
					String sendEvent = "location";
					String setStatus = "danger";
					String setStatusSafe = "safe";

					// set safe
					if (receviedCommand.contains(setStatusSafe)) {
						System.out.println("in set SAFE status client");
						String sendTest = "give";
						byte[] b = sendTest.getBytes();
						DataOutputStream dOut = null;
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.writeInt(b.length);
							dOut.write(b);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							in = socket.getInputStream();
						} catch (IOException ex) {
							System.out.println("Can't get socket input stream. ");
						}

						try {
							out = new FileOutputStream("JsonFiles\\clientStatus.json");
						} catch (FileNotFoundException ex) {
							System.out.println("File not found. ");
						}

						byte[] bytes = new byte[16 * 1024];

						int count;
						try {
							while ((count = in.read(bytes)) > 0) {
								out.write(bytes, 0, count);
							}
							out.close();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						if (UpdateClientStatusJSON.UpdateStatusSafe(context)) {
							System.out.println("Edit status to safe done");
						}

					}

//set danger 					

					if (receviedCommand.contains(setStatus)) {
						System.out.println("in set status client");
						String sendTest = "give";
						byte[] b = sendTest.getBytes();
						DataOutputStream dOut = null;
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.writeInt(b.length);
							dOut.write(b);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							in = socket.getInputStream();
						} catch (IOException ex) {
							System.out.println("Can't get socket input stream. ");
						}

						try {
							out = new FileOutputStream("JsonFiles\\clientStatus.json");
						} catch (FileNotFoundException ex) {
							System.out.println("File not found. ");
						}

						byte[] bytes = new byte[16 * 1024];

						int count;
						try {
							while ((count = in.read(bytes)) > 0) {
								out.write(bytes, 0, count);
							}
							out.close();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						if (UpdateClientStatusJSON.UpdateStatus(context)) {
							System.out.println("Edit status to danger done");
						}

					}
//send event to android
					if (receviedCommand.contains(sendEvent)) {
						System.out.println("in recive location");
						String sendTest = "give";
						byte[] b = sendTest.getBytes();
						DataOutputStream dOut = null;
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.writeInt(b.length);
							dOut.write(b);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// Aici

						try {
							in = socket.getInputStream();
						} catch (IOException ex) {
							System.out.println("Can't get socket input stream. ");
						}

						try {
							out = new FileOutputStream("JsonFiles\\name_client.json");
						} catch (FileNotFoundException ex) {
							System.out.println("File not found. ");
						}

						byte[] bytes = new byte[16 * 1024];

						int count;
						try {
							while ((count = in.read(bytes)) > 0) {
								out.write(bytes, 0, count);
							}
							out.close();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						if (CreateEditClientJson.ConvertToClientFromJSON(context))

						{
							System.out.println("Adaugare sau editare client gata");
						}

						String nameOfClient = ReadNameClientJSON.readName(context);

						/*
						 * jsonEventsForClient = CreateEventForClient.getEvent_JSON(context,
						 * nameOfClient);
						 * 
						 * if (jsonEventsForClient != null) { byte[] clientListByte =
						 * jsonEventsForClient.getBytes();
						 * 
						 * try { dOut = new DataOutputStream(socket.getOutputStream());
						 * dOut.writeInt(clientListByte.length); dOut.write(clientListByte); } catch
						 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
						 * }
						 */
						System.out.println("End of read location funct");

					}

//Adaugare Evnt

					if (receviedCommand.contains(addEventComparator)) {
						System.out.println("in adaugare");

						String sendTest = "send";
						byte[] b = sendTest.getBytes();
						DataOutputStream dOut = null;
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.write(b);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							in = socket.getInputStream();
						} catch (IOException ex) {
							System.out.println("Can't get socket input stream. ");
						}

						try {
							out = new FileOutputStream("JsonFiles\\eventAdd.json");
						} catch (FileNotFoundException ex) {
							System.out.println("File not found. ");
						}

						byte[] bytes = new byte[16 * 1024];

						int count;
						try {
							while ((count = in.read(bytes)) > 0) {
								System.out.println(new String(bytes));
								out.write(bytes, 0, count);
							}
							out.close();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						System.out.println("Prepare to add to DB");

						if (CreateEventFromJSON.ConvertToEvent(context)) {
							System.out.println("Adaugat");

						} else {
							System.out.println("Something went wrong when trying to add");
						}

					}

					System.out.println("-");

//Trimit lista Clientilor	

					if (receviedCommand.contains(readClientsComparator)) {
						System.out.println("In afisare clienti");
						DataOutputStream dOut = null;
						String clientListJson = CreateJSONClient.GetClient_JSON(context);
						byte[] clientListByte = clientListJson.getBytes();
						clientListJson.getBytes();
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.write(clientListByte);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("Sended");
					}

//Trimit lista events

					if (receviedCommand.contains(readEventsComparator)) {
						System.out.println("In afisare events");
						DataOutputStream dOut = null;
						String clientListJson = CreateJSONEvents.GetEvent_JSON(context);
						byte[] clientListByte = clientListJson.getBytes();
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.write(clientListByte);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("Sended");
					}

// Edit event status					
					if (receviedCommand.contains(editEventComparator)) {
						System.out.println("in edit event");

						String sendTest = "edit";
						byte[] b = sendTest.getBytes();
						DataOutputStream dOut = null;
						try {
							dOut = new DataOutputStream(socket.getOutputStream());
							dOut.write(b);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							in = socket.getInputStream();
						} catch (IOException ex) {
							System.out.println("Can't get socket input stream. ");
						}

						try {
							out = new FileOutputStream("JsonFiles\\eventEdit.json");
						} catch (FileNotFoundException ex) {
							System.out.println("File not found. ");
						}

						byte[] bytes = new byte[16 * 1024];

						int count;
						try {
							while ((count = in.read(bytes)) > 0) {
								out.write(bytes, 0, count);
							}
							out.close();
						} catch (IOException e1) {

							e1.printStackTrace();
						}

						System.out.println("Prepare to edit into DB");

						if (EditEventFromJson.EditEventFromJSON(context)) {
							System.out.println("Editat");

						} else {
							System.out.println("Something went wrong when trying to edit");
						}

					}
				}

			}

		}

		if (command.equalsIgnoreCase("STOP"))

		{

			stop.setValue(true);

		}
	}
}
