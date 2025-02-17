/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.exhaustiveTests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.parseVerilog.Convert;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.BU.netsynth.Utilities;
import org.cellocad.BU.precomputation.genVerilogFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class Allnin1out {
    public static boolean verifyNin1out(List<NetSynthSwitch> switches,int size) throws JSONException{
        boolean result = true;
        
        String filepath = "";
        filepath = Utilities.getNetSynthResourcesFilepath();
        filepath += "testVerilog.v";
        int ttSize = (int)Math.pow(2, size);
        int noOfTT = (int)Math.pow(2, ttSize);
        NetSynth netSynth = new NetSynth("threeInOneOut");
        netSynth.swapCount = 3;
        for(int i=0;i<noOfTT;i++){
            try {
                
                if(i==0 || i==(noOfTT-1)){
                    continue;
                }
                System.out.println("Truth Table :: " +i);
                List<String> verilogFileLines = new ArrayList<String>();
                verilogFileLines = genVerilogFile.createSingleOutpVerilogFile(size, i);
                File file = new File(filepath);
                Writer output = new BufferedWriter(new FileWriter(file));
                for(String line:verilogFileLines){  
                    String lineToWrite = line + "\n";
                    output.write(lineToWrite);
                    output.flush();
                }
                
                List<String> inputnames = new ArrayList<String>();
                for(int j=1;j<=size;j++){
                    String inpName = "inp" + j;
                    inputnames.add(inpName);
                }
                List<DGate> netlist = new ArrayList<DGate>();
                
                long startTime = System.nanoTime();
                netlist = netSynth.getNetlist(filepath, switches);
                
                long endTime = System.nanoTime();
                
                long duration = (endTime - startTime)/1000000;
                System.out.println("That took ::"+duration+" milliseconds");
                String tt = BooleanSimulator.getTruthTable(netlist, inputnames).get(0); 
                
                System.out.println("Circuit Size = " + netlist.size()+"\n");
                
                assertFalse(NetSynth.hasCycles(netlist));
                
                int ttIntVal = Convert.bintoDec(tt);
                if(ttIntVal != i){
                    result = false;
                    System.out.println("Circuit "+i+ " does not work\n");
                }
                
                //file.delete();
                
                //result = true;
                //System.out.println("Verilog File Lines" + verilogFileLines);
            } catch (IOException ex) {
                Logger.getLogger(Allnin1out.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        return result;
    }
    
    //@Test
    public void testAllCombinations() throws JSONException{
       boolean result;
       List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
       //switches.add(NetSynthSwitches.espresso);
       switches.add(NetSynthSwitch.output_or);
       //switches.add(NetSynthSwitch.abc);
       //switches.add(NetSynthSwitch.noswap);
       int size = 3;
       result = verifyNin1out(switches,size);
       String assertMessage = size + " Input 1 Output Test Failed.";
       assertTrue(assertMessage,result);
    }
    
}
