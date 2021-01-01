package com.rimdev.demo.tipcalc;

import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.text.TextFilter;
import net.rim.device.api.ui.text.NumericTextFilter;


public class TipCalcMainScreen extends MainScreen 
{
	protected EditField amount = new EditField();

    public TipCalcMainScreen()
    {
        // Each screen can have a field in the Title section. 
        LabelField title = new LabelField("TipCalc" , 
        		LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
        // Set the title to the label.
        setTitle(title);

        // In order to keep things nice and easy for the user, set a filter preventing
        // them from entering anything but numbers
        NumericTextFilter amt_filter = new NumericTextFilter(TextFilter.REAL_NUMERIC);
        amount.setFilter(amt_filter);
        
        // setup the EditField to accept the Bill Amount
        amount.setLabel("Bill Amount: $");
        // add the field to the screen
        add(amount);
    }

    // Menu items ---------------------------------------------------------------
    MenuItem _calculateAction = new MenuItem("Calculate" , 100000, 10)
    {       
        public void run()
        {
           Dialog.alert("The tip is $"+Double.toString(calculateTip()));
        }
    };

    protected void makeMenu(Menu menu, int instance)
    {
        menu.add(_calculateAction);            
        super.makeMenu(menu, instance);
    }
    
    protected double calculateTip()
    {
        double billamount;
        // Convert the text entered into the textfield into 
        // a floating point number.
        try
        {
            billamount = Double.valueOf(amount.getText().trim()).floatValue();
        }
        catch (NumberFormatException nfe)
        {
            billamount = 0;
        }

        double tipamount = billamount * 0.10;

        // round the computed amount to two decimal places.
        tipamount += 0.005;
        tipamount *= 100.0;
        int tip = (int)tipamount;
        tipamount = (double)tip / 100.0;

        return tipamount;
    }

    // return true to allow an exit without displaying the save prompt
    protected boolean onSavePrompt()
    {
        return true;
    }

}
