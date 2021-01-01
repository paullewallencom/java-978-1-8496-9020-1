package net.rimdev.demo.FieldSampler;

import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.ui.text.*;
import java.util.Date;


public class FieldSamplerScreen extends MainScreen 
{
	// Class members
	protected String listMembers[] = {"Item1","Item2","Item3"};
	
	public FieldSamplerScreen ()
	{
		setTitle(new LabelField("Field Sampler!"));
		createFields();
	}
	
	private void createFields()
	{
		// Demonstrate a SeparatorField
		add(new SeparatorField());

		// Demonstrate a LabelField
		add(new LabelField("Label Field"));
		
		// A separator field between each type of control
		add(new SeparatorField());

		// Retrieve one of the predefined system bitmaps
		Bitmap bminfo = Bitmap.getPredefinedBitmap(Bitmap.INFORMATION);
		add(new BitmapField(bminfo));
		// Retrieve a bitmap that has been added to the project.
		Bitmap bmPackt = Bitmap.getBitmapResource("PacktLogoSmall.png");
		add(new BitmapField(bmPackt));
		
		// A separator field between each type of control
		add(new SeparatorField());

		String[] choices = {"Choice1","Choice2","Choice3"};
		//create an ObjectChoice
		ObjectChoiceField objChoice = new ObjectChoiceField("Object Choice Field",choices);
		add(objChoice);
		// Another way to create the same ChoiceField.
		ObjectChoiceField objChoice2 = new ObjectChoiceField();
		objChoice2.setLabel("Another way");
		objChoice2.setChoices(choices);
		add(objChoice2);
		
		// Create a numeric Choice showing values between 10 and 20 stepping by 2s
		NumericChoiceField numChoice = new NumericChoiceField("Numeric Choice Field",10,20,2);
		add(numChoice);
		
		// A separator field between each type of control
		add(new SeparatorField());		

		// A gauge from 0 to 10 with the initial value being 8.
		GaugeField gaugeField = new GaugeField("Editable", 0, 10, 8,Field.EDITABLE|Field.FOCUSABLE);
		add(gaugeField);
		
		// A gauge from 0 to 100 with the initial value being 35 and using no special styles
		GaugeField progress = new GaugeField("Normal Style",0,100,35,Field.FOCUSABLE);
		add(progress);
		
		// A gauge from 0 to 20 with the initial value being 5 and using the NO_TEXT flag.
		GaugeField notext = new GaugeField("NoText Style",0,20,5,Field.FOCUSABLE|GaugeField.NO_TEXT);
		add(notext);

		// A gauge from -19 to 17 with the initial value being 4 and using the PERCENT flag.
		GaugeField percent = new GaugeField("Percent Style",-19,17,4,Field.FOCUSABLE|GaugeField.PERCENT);
		add(percent);

		// A separator field between each type of control
		add(new SeparatorField());
				
		Date now = new Date();
		DateField datetimeField = new DateField("DateTime Field",now.getTime(),DateFormat.getInstance(DateFormat.DATETIME_DEFAULT));
		add(datetimeField);
		
		DateField dateField = new DateField();
		dateField.setLabel("Date Field");
		dateField.setDate(now);
		dateField.setFormat(DateFormat.getInstance(DateFormat.DATE_DEFAULT));
		add(dateField);
		
		DateField timeField = new DateField("Time Field",now.getTime(),DateFormat.getInstance(DateFormat.TIME_DEFAULT));
		add(timeField);
		
		// A separator field between each type of control
		add(new SeparatorField());
		
		CheckboxField chkField = new CheckboxField("Checkbox Field", false);
		add(chkField);
		CheckboxField chkField2 = new CheckboxField("FIELD_RIGHT",false,Field.FIELD_RIGHT);
		add(chkField2);
		
		// A separator field between each type of control
		add(new SeparatorField());
		
		// Create a radio button group 
		RadioButtonGroup rdoGroup = new RadioButtonGroup();
		RadioButtonField rdo1 = new RadioButtonField("Radio Field 1");
		RadioButtonField rdo2 = new RadioButtonField("Radio Field 2");
		RadioButtonField rdo3 = new RadioButtonField("Radio Field 3");
		// add the radio buttons to the radio button group. 
		//Adding them to the group is what ensures only one field is selected
		rdoGroup.add(rdo1);
		rdoGroup.add(rdo2);
		rdoGroup.add(rdo3);

		// Each field must still be added to the screen. 
		// They do not need to be added in the same order as the they were with the group 
		add(rdo3);
		add(rdo2);
		add(rdo1);

		// set Field 1 (which is at the bottom of the screen because they are added in reverse order) to be selected
		rdoGroup.setSelectedIndex(0);

		// A separator field between each type of control
		add(new SeparatorField());
		
		String initialValue = "";
		BasicEditField basic = new BasicEditField("Basic: ",initialValue);
		basic.setMaxSize(25);
		add(basic);

		BasicEditField phone = new BasicEditField("Phone: ",initialValue,25,BasicEditField.FILTER_PHONE);
		add(phone);

		BasicEditField phone2 = new BasicEditField("Phone2: ",initialValue);
		phone2.setFilter(new PhoneTextFilter());
		phone2.setMaxSize(25);
		add(phone2);

		// A separator field between each type of control
		add(new SeparatorField());

		BasicEditField edit = new EditField("Edit: ",initialValue);
		edit.setMaxSize(25);
		add(edit);
		// A separator field between each type of control
		add(new SeparatorField());

		PasswordEditField pass = new PasswordEditField("Password: ",initialValue);
		add(pass);
		// A separator field between each type of control
		add(new SeparatorField());

		EmailAddressEditField addr = new EmailAddressEditField("Email: ",initialValue);
		add(addr);
		
		// A separator field between each type of control
		add(new SeparatorField());

		AutoTextEditField auto = new AutoTextEditField("Auto: ", initialValue);
		add(auto);

		AutoTextEditField autoNoCap = new AutoTextEditField("AutoNoCap: ", initialValue, 25,AutoTextEditField.AUTOCAP_OFF);
		add(autoNoCap);

		AutoTextEditField autoNoPunc = new AutoTextEditField("AutoNoPunc: ", initialValue,25,AutoTextEditField.AUTOPERIOD_OFF);
		add(autoNoPunc);

		AutoTextEditField autoOFF = new AutoTextEditField("AutoOFF: ",initialValue,25,AutoTextEditField.AUTOREPLACE_OFF );
		add(autoOFF);

		// A separator field between each type of control
		add(new SeparatorField());

		ActiveAutoTextEditField activeauto = new ActiveAutoTextEditField("ActiveAuto: ","Visit Packt Publishing online at www.packtpub.com");
		add(activeauto);

//		// A separator field between each type of control
//		add(new SeparatorField());
//		
//		EditField nonedit = new EditField("Non Editable: ", "Some text",25,EditField.READONLY);
//		add(nonedit);

		// A separator field between each type of control
		add(new SeparatorField());

		Font someFont;
		try 
		{
			someFont = FontFamily.forName("BBClarity").getFont(FontFamily.SCALABLE_FONT, 10);
		}
		catch (ClassNotFoundException e) 
		{ 
			someFont = Font.getDefault();
		}
		Font fonts[] = new Font[5]; 
		fonts[0] = Font.getDefault(); 
		fonts[1] = Font.getDefault().derive(Font.BOLD); 
		fonts[2] = Font.getDefault().derive(Font.UNDERLINED); 
		fonts[3] = Font.getDefault().derive( Font.ITALIC); 
		fonts[4] = someFont; 
		
		byte attributes[] = new byte[8];
		int offsets[] = new int[9];
		
		// must always start the offset at 0
		offsets[0] = 0;
		attributes[0] = 0; // Default font
		
		// The next change will happen at offset 2
		// for the word 'RichTextField'
		offsets[1] = 2;
		attributes[1] = 1; //Bold
		
		// then switch back to the default
		offsets[2] = 15;
		attributes[2] = 0; //default
		
		//italicize the word 'display'
		offsets[3] = 20;
		attributes[3] = 3; //default
		
		// then switch back to the default
		offsets[4] = 27;
		attributes[4] = 0; //default
		
		// underline the word 'many'
		offsets[5] = 35;
		attributes[5] = 2; //default
		
		// immediately bold italics the
		// word 'different'
		offsets[6] = 40;
		attributes[6] = 4; //default
		
		// then switch back to the default
		offsets[7] = 50;
		attributes[7] = 0; //default
		
		// the last entry in offset is the last character in the text.
		offsets[8] = 57;
		// no attribute is given. 
		// offset array must be 1 greater than the attribute array.
		
		RichTextField rich = new RichTextField();
		// a ruler like this helps when counting characters.
		//            012345678901234567890123456789012345678901234567890123456
		rich.setText("A RichTextField can display text in many different fonts.",offsets,attributes,fonts);
		add(rich);
		// A separator field between each type of control
		add(new SeparatorField());
		
		int bg[] = new int[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,Color.BLUE};
		int fg[] = new int[]{Color.BLACK, Color.GREEN, Color.RED,Color.PURPLE, Color.WHITE};
		
		ActiveRichTextField activeRich = new ActiveRichTextField("");
		initialValue = "A RichTextField can display text in many different fonts. An ActiveRichTextField also displays Active regions like www.packtpub.com.";
		offsets[8] = initialValue.length();
		activeRich.setText(initialValue,offsets,attributes,fonts,fg,bg);
		add(activeRich);
		
		// A separator field between each type of control
		add(new SeparatorField());		
	}
	
	  // Menu items ---------------------------------------------------------------
    MenuItem _getValueAction = new MenuItem("GetValue" , 100000, 10)
    {    
    	// This function will demonstrate getting the value from the field with focus. 
    	// To do this, we must determine which field has focus.
        public void run()
        {
        	getValue();
        }
    };
    

    protected void getValue()
    {
       Field f = getFieldWithFocus();

       if (f instanceof ObjectChoiceField)
       {
    	   ObjectChoiceField choice = (ObjectChoiceField) f;
    	   int index = choice.getSelectedIndex();
    	   Object o = choice.getChoice(index);
    	   String s = (String) o;
           Dialog.alert("The selected value is " + s);
       }
       if (f instanceof NumericChoiceField)
       {
    	   NumericChoiceField choice = (NumericChoiceField) f;
    	   int n = choice.getSelectedValue();
           Dialog.alert("The selected value is " + Integer.toString(n));
       }
       if (f instanceof RadioButtonField)
       {
    	   RadioButtonField rdo = (RadioButtonField) f;
    	   RadioButtonGroup rdogroup = rdo.getGroup();
    	   int index = rdogroup.getSelectedIndex();
    	   Dialog.alert("The radio button index is " + index);
       }
       if (f instanceof CheckboxField)
       {
    	   CheckboxField check = (CheckboxField) f;
    	   String value;
    	   if (check.getChecked())
    	   {
    		   value = "Checked";
    	   }
    	   else
    	   {
    		   value = "Unchecked";
    	   }
    	   Dialog.alert("The checkbox is " + value);
       }
       if (f instanceof DateField)
       {
    	   DateField df = (DateField) f;
    	   long datevalue = df.getDate();
       	   Dialog.alert("The date is " +  DateFormat.getInstance(DateFormat.DATETIME_DEFAULT).formatLocal(datevalue));
       }
       if (f instanceof GaugeField)
       {
           new Thread(new Runnable()
           {
               public void run()
               {
            	   GaugeField g = (GaugeField) FieldSamplerScreen.this.getFieldWithFocus();
            	   g.setValue(g.getValueMin());
    			   for (int i = g.getValueMin(); i < g.getValueMax();i++)
    			   {
    				   try {
    					   Thread.sleep(1000);
    				   } catch (InterruptedException e) {
    					   	// TODO Auto-generated catch block
    					   e.printStackTrace();
    				   }
                	   UiApplication.getUiApplication().invokeLater(new Runnable() {
                		   public void run() {

                        	   GaugeField g = (GaugeField) FieldSamplerScreen.this.getFieldWithFocus();
                        	   g.setValue(g.getValue()+1);
                		   }
                	   });
    			   }
    		   }
           }).start();
	   }
       if (f instanceof TextField)
       {
    	   TextField t = (TextField) f;
       	   Dialog.alert("The text is " +  t.getText());    	   
       }
    }

	public void fieldChanged( Field field, int context )
	{
		getValue();
	}
    
    public void makeMenu(Menu m, int instance)
    {
    	m.add(_getValueAction);
    	super.makeMenu(m,instance);
    }
}
