package com.rimdev.demos.TimeOff;

import java.util.Date;

import javax.microedition.pim.Contact;
import javax.microedition.pim.Event;
import javax.microedition.pim.EventList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.RepeatRule;

import net.rim.blackberry.api.mail.Address;
import net.rim.blackberry.api.mail.AddressException;
import net.rim.blackberry.api.mail.Message;
import net.rim.blackberry.api.mail.MessagingException;
import net.rim.blackberry.api.mail.Multipart;
import net.rim.blackberry.api.mail.TextBodyPart;
import net.rim.blackberry.api.mail.Transport;
import net.rim.blackberry.api.pdap.BlackBerryContact;
import net.rim.blackberry.api.pdap.BlackBerryContactList;
import net.rim.blackberry.api.pdap.BlackBerryEvent;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ActiveAutoTextEditField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EmailAddressEditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class TimeOffMainScreen extends MainScreen {

	protected EmailAddressEditField _To = new EmailAddressEditField("Address: ", "");
	protected DateField _StartDate = new DateField();
	protected DateField _EndDate = new DateField();
	protected ActiveAutoTextEditField _Comments = new ActiveAutoTextEditField("", "");
	
	
	public TimeOffMainScreen(){
		setTitle (new LabelField("Gimme Time Off!"));
		
		_To.setMaxSize(100);
		
		_StartDate.setLabel("Starting Date: ");
		_EndDate.setLabel("Ending Date: ");
		
		_StartDate.setFormat(DateFormat.getInstance(DateFormat.DATE_DEFAULT));
		_EndDate.setFormat(DateFormat.getInstance(DateFormat.DATE_DEFAULT));
		
		_StartDate.setDate(new Date());
		_EndDate.setDate(new Date());
		
		_Comments.setMaxSize(2000);
		
		add(_To);
		add(new SeparatorField());
		add(_StartDate);
		add(_EndDate);
//		add (new SeparatorField());
		add(new LabelField("Comments"));
		add(new SeparatorField());
		add(_Comments);
		
		this.addMenuItem(_SendRequestAction);
		this.addMenuItem(_AddTestAddressesAction);
	}
	protected void makeMenu(Menu m, int context)
	{
		if (this.getFieldWithFocus() == _To  && 
				context != Menu.INSTANCE_CONTEXT_SELECTION)
		{
			m.add(_AddressBookAction);
		}

		super.makeMenu(m,context);
	}
	
	  // Menu items ---------------------------------------------------------------
	protected MenuItem _SendRequestAction = new MenuItem("Send Request" , 100000, 10)
	{    
        public void run()
        {
        	sendRequest();
        	addEvent();
        	close();
        }
    };
    
    protected boolean onSave()
    {
    	return true;
    }
    
	protected MenuItem _AddTestAddressesAction = new MenuItem("Add Test Data" , 800000, 50)
	{    
	    public void run()
	    {
	    	PIM pimInstance = PIM.getInstance();
			try {
				// TODO: Create test contacts
				BlackBerryContactList contacts = (BlackBerryContactList)pimInstance.openPIMList(PIM.CONTACT_LIST, PIM.READ_WRITE);
				BlackBerryContact newContact1 = (BlackBerryContact) contacts.createContact();
				BlackBerryContact newContact2 = (BlackBerryContact) contacts.createContact();
				BlackBerryContact newContact3 = (BlackBerryContact) contacts.createContact();
				
				String[] names = new String[contacts.stringArraySize(BlackBerryContact.NAME)];
				names[BlackBerryContact.NAME_FAMILY] = "Smith";
				names[BlackBerryContact.NAME_GIVEN] = "John";
				if (contacts.isSupportedArrayElement(Contact.NAME, Contact.NAME_SUFFIX))
				{
					names[BlackBerryContact.NAME_SUFFIX]="Jr";
				}
				newContact1.addStringArray(BlackBerryContact.NAME, BlackBerryContact.ATTR_NONE, names);
				
				names[Contact.NAME_FAMILY] = "Doe";
				names[Contact.NAME_GIVEN] = "John";
				names[Contact.NAME_PREFIX] = "Dr.";
				newContact2.addStringArray(Contact.NAME, Contact.ATTR_NONE, names);
				
				//TODO: Add Phone numbers
				newContact1.addString(BlackBerryContact.TEL,BlackBerryContact.ATTR_MOBILE,"555-555-1212");
				newContact2.addString(Contact.TEL,Contact.ATTR_HOME,"555-555-1234");
				newContact2.addString(Contact.TEL,Contact.ATTR_FAX,"555-555-9999");
				newContact2.addString(Contact.TEL,Contact.ATTR_MOBILE,"555-555-1313");
				// This is bad!
				newContact2.addString(Contact.TEL,Contact.ATTR_FAX,"555-555-1414");
				
				// The proper way to change a value
				int count = newContact2.countValues(Contact.TEL);
				for (int i = 0; i < count; i++)
				{
					if ((newContact2.getAttributes(Contact.TEL, i) & Contact.ATTR_HOME) == Contact.ATTR_HOME)
					{
						newContact2.setString(Contact.TEL, i, Contact.ATTR_HOME, "555-555-4321");
					}
				}
	
				//TODO: Add Email Addresses
				newContact1.addString(Contact.EMAIL,Contact.ATTR_NONE,"John@Test.com");
				
				newContact2.addString(Contact.EMAIL,Contact.ATTR_NONE,"JDoe@test.com");
				newContact2.addString(Contact.EMAIL,Contact.ATTR_NONE,"JohnDoe@test.com");
				newContact2.addString(Contact.EMAIL,Contact.ATTR_NONE,"Admintest.com");
	
				//TODO: Add Addresses
				String[] Address1 = new String[contacts.stringArraySize(BlackBerryContact.ADDR)];
				String[] Address2 = new String[contacts.stringArraySize(BlackBerryContact.ADDR)];
				
				Address1[BlackBerryContact.ADDR_STREET] = "123 Main St.";
				Address1[BlackBerryContact.ADDR_EXTRA] = "Apt. 4";
				Address1[BlackBerryContact.ADDR_LOCALITY] = "AnyTown";
				Address1[BlackBerryContact.ADDR_REGION] = "AnyState";
				Address1[BlackBerryContact.ADDR_COUNTRY] = "USA";
				Address1[BlackBerryContact.ADDR_POSTALCODE] = "12345";
				newContact1.addStringArray(BlackBerryContact.ADDR,BlackBerryContact.ATTR_HOME,Address1);
				
				
				Address1[BlackBerryContact.ADDR_STREET] = "345 Main St.";
				Address1[BlackBerryContact.ADDR_LOCALITY] = "AnyTown";
				Address1[BlackBerryContact.ADDR_REGION] = "AnyState";
				Address1[BlackBerryContact.ADDR_COUNTRY] = "USA";
				Address1[BlackBerryContact.ADDR_POSTALCODE] = "12345";
				
				Address2[BlackBerryContact.ADDR_STREET] = "20 N Oak St.";
				Address2[BlackBerryContact.ADDR_LOCALITY] = "AnyTown";
				Address2[BlackBerryContact.ADDR_REGION] = "AnyState";
				Address2[BlackBerryContact.ADDR_COUNTRY] = "USA";
				Address2[BlackBerryContact.ADDR_EXTRA] = "Suite 200";
				Address1[BlackBerryContact.ADDR_POSTALCODE] = "12345";
				
				newContact2.addStringArray(BlackBerryContact.ADDR,BlackBerryContact.ATTR_HOME,Address1);
				newContact2.addStringArray(BlackBerryContact.ADDR,BlackBerryContact.ATTR_WORK,Address2);

				newContact1.commit();
				newContact2.commit();
				
			} catch (PIMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	};
   
    protected MenuItem _AddressBookAction = new MenuItem("Address Book" , 100000, 10)
    {    
        public void run()
        {
			PIM pim = PIM.getInstance();
			try {
				BlackBerryContactList contacts = (BlackBerryContactList)pim.openPIMList(PIM.CONTACT_LIST, PIM.READ_WRITE);
				BlackBerryContact selected = (BlackBerryContact) contacts.choose();
				if (selected != null)
				{
					int EmailAddressCount = selected.countValues(Contact.EMAIL);
					// check to make sure that there is an Email address for this contact.
					if ( EmailAddressCount > 0)
					{
						String selectedEmail;
						// If there is more than just one email, display a dialog to choose
						if (EmailAddressCount > 1)
						{
							String[] Addresses = new String[EmailAddressCount];
							int[] Values = new int[EmailAddressCount];
							for (int i = 0; i < EmailAddressCount; i++)
							{
								Addresses[i] = selected.getString(Contact.EMAIL, i);
								Values[i] = i;
							}
							Dialog dlg = new Dialog("Select which address to use.",Addresses,Values,0,Bitmap.getPredefinedBitmap(Bitmap.QUESTION));
							int selectedAddr = dlg.doModal();
							selectedEmail = Addresses[selectedAddr];
						}
						else
						{
							selectedEmail = selected.getString(Contact.EMAIL, 0);
						}
						
						TimeOffMainScreen theScreen = (TimeOffMainScreen)UiApplication.getUiApplication().getActiveScreen();
						theScreen._To.setText(selectedEmail);
					}
					else
					{
						Dialog.alert("This contact has no Email Addresses.");
					}
				}
			} catch (PIMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };

    protected void sendRequest()
    {
		StringBuffer msgBody = new StringBuffer();
		msgBody.append("A new TimeOff application requestion has been submitted.\nFrom: ");
		msgBody.append(Integer.toHexString(DeviceInfo.getDeviceId()));
		msgBody.append("\nStarting: ");
		msgBody.append(DateFormat.getInstance(DateFormat.DATE_DEFAULT).formatLocal(_StartDate.getDate()));
		msgBody.append("\nEnding: ");
		msgBody.append(DateFormat.getInstance(DateFormat.DATE_DEFAULT).formatLocal(_EndDate.getDate()));
		msgBody.append("\nComments: ");
		msgBody.append(_Comments.getText());
		
		Message newMsg = new Message();
		try {
			Address recipient = null;
			recipient = new Address(_To.getText(), "TimeOff Recipient");
			newMsg.addRecipient(Message.RecipientType.TO,recipient);
			newMsg.setSubject("Time off Request");
			newMsg.setContent(msgBody.toString());		
			Transport.send(newMsg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void addEvent ()
    {
		PIM pim = PIM.getInstance();
		EventList events;
		try {
			events = (EventList)pim.openPIMList(PIM.EVENT_LIST, PIM.READ_WRITE);
			BlackBerryEvent newEvent = (BlackBerryEvent) events.createEvent();
			newEvent.addString(BlackBerryEvent.SUMMARY, BlackBerryEvent.ATTR_NONE, "Requested time off.");
			newEvent.addString(BlackBerryEvent.LOCATION, BlackBerryEvent.ATTR_NONE, "Special place");
			newEvent.addDate(BlackBerryEvent.START,BlackBerryEvent.ATTR_NONE,_StartDate.getDate());
			newEvent.addDate(BlackBerryEvent.END, BlackBerryEvent.ATTR_NONE, _EndDate.getDate());
			newEvent.addString(BlackBerryEvent.NOTE, BlackBerryEvent.ATTR_NONE, _Comments.getText());
			newEvent.addBoolean(BlackBerryEvent.ALLDAY, BlackBerryEvent.ATTR_NONE, true);
			newEvent.addInt(BlackBerryEvent.FREE_BUSY, BlackBerryEvent.ATTR_NONE, BlackBerryEvent.FB_BUSY);
			newEvent.commit();
			
			BlackBerryEvent reminder = (BlackBerryEvent) events.createEvent();
			reminder.addString(BlackBerryEvent.SUMMARY, BlackBerryEvent.ATTR_NONE, "Check on request");
			reminder.addDate(BlackBerryEvent.START, BlackBerryEvent.ATTR_NONE, new Date().getTime()+86400000);
			reminder.addDate(BlackBerryEvent.END, BlackBerryEvent.ATTR_NONE, new Date().getTime()+86400000);
			
			RepeatRule repeat = new RepeatRule();
			repeat.setInt(RepeatRule.FREQUENCY,RepeatRule.DAILY);
			repeat.setInt(RepeatRule.INTERVAL, 1);
			repeat.setDate(RepeatRule.END, new Date().getTime()+(86400000 * 7));
			
			reminder.setRepeat(repeat);
			reminder.commit();
			
		} catch (PIMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
     }
}
