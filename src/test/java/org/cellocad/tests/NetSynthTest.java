/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cellocad.BU.dom.DAGW;
import org.cellocad.BU.dom.DGate;
import org.cellocad.BU.dom.DGateType;
import org.cellocad.BU.dom.DWire;
import org.cellocad.BU.dom.DWireType;
import org.cellocad.BU.netsynth.NetSynth;
import org.cellocad.BU.netsynth.NetSynthSwitch;
import org.cellocad.BU.simulators.BooleanSimulator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author prash
 */
public class NetSynthTest {
    
    
    //@Test //Work on this later..
    public void testGetNetlistCode(){
        System.out.println("\ntestGetNetlistCode");
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
        String verilogCode = "module A(output out1,  input in1, in2);\n" +
                             "  always@(in1,in2)\n" +
                             "    begin\n" +
                             "      case({in1,in2})\n" +
                             "        2'b00: {out1} = 1'b0;\n" +
                             "        2'b01: {out1} = 1'b0;\n" +
                             "        2'b10: {out1} = 1'b1;\n" +
                             "        2'b11: {out1} = 1'b0;\n" +
                             "      endcase\n" +
                             "    end\n" +
                             "endmodule";
        NetSynth netsynth = new NetSynth("deleteTest");
        System.out.println("Folder :: " + netsynth.getResultsPath());
        
        
        NetSynth.printNetlist(netsynth.getNetlistCode(verilogCode, switches)); 
        netsynth.cleanDirectory();
    }
    
    //@Test //Work on this later
    public void testGetNetlistCodeSingleLine(){
        System.out.println("\ntestGetNetlistCodeSingleLine");
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
        String verilogCode = "module A(output out1,  input in1, in2); always@(in1,in2) begin case({in1,in2}) 2'b00: {out1} = 1'b0; 2'b01: {out1} = 1'b0; 2'b10: {out1} = 1'b1; 2'b11: {out1} = 1'b0; endcase end endmodule";
        NetSynth netsynth = new NetSynth();
        NetSynth.printNetlist(netsynth.getNetlistCode(verilogCode, switches));
        
    }
    
    //@Test
    public void testRenameWire(){
        List<DGate> netlist1 = getTestNetlist1();
        List<DGate> netlist2 = getTestNetlist1();
        List<DGate> netlist3 = getTestNetlist1();
        List<DGate> netlist4 = getTestNetlist1();
        
        NetSynth.renameWires(netlist1);
        NetSynth.renameWires(netlist2,"testWire");
        NetSynth.renameWires(netlist3, 3);
        NetSynth.renameWires(netlist4, "00Wire", 15);
    
        System.out.println("##### Base Netlist ######");
        NetSynth.printNetlist(getTestNetlist1());
    
        
        System.out.println("\n\n##### NETLIST 1 ######");
        NetSynth.printNetlist(netlist1);
        
        System.out.println("\n\n##### NETLIST 2 ######");
        NetSynth.printNetlist(netlist2);
        
        System.out.println("\n\n##### NETLIST 3 ######");
        NetSynth.printNetlist(netlist3);
        
        System.out.println("\n\n##### NETLIST 4 ######");
        NetSynth.printNetlist(netlist4);
    
    }
    
    
    //@Test
    public void testRemoveDoubleInverters(){
        List<DGate> test1 = getTestNetlist1();
        List<DGate> test2 = getTestNetlist2();
        List<DGate> test3 = getTestNetlist3();
        List<DGate> test4 = getTestNetlist4();
        List<DGate> test5 = getTestNetlist5();
        List<DGate> test6 = getTestNetlist6();
        
        /*NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test1);
        test1 = NetSynth.removeDoubleInverters(test1);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test1);
        
        NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test2);
        test2 = NetSynth.removeDoubleInverters(test2);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test2);
        
        NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test3);
        test3 = NetSynth.removeDoubleInverters(test3);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test3);
        
        NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test4);
        test4 = NetSynth.removeDoubleInverters(test4);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test4);
        
        
        NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test5);
        test5 = NetSynth.removeDoubleInverters(test5);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test5);
        
        NetSynth.printDebugStatement("BEFORE");
        NetSynth.printNetlist(test6);
        test6 = NetSynth.removeDoubleInverters(test6);
        NetSynth.printDebugStatement("AFTER");
        NetSynth.printNetlist(test6);*/
        
        
        
    }
    
    //@Test
    public void testGet2inNetlist(){
        String verilogLines = "module A(output out1, input in1, in2);\n"
                + "  always@(in1,in2)\n"
                + "    begin\n"
                + "      case({in1,in2})\n"
                + "        2'b00: {out1} = 1'b0;\n"
                + "        2'b01: {out1} = 1'b1;\n"
                + "        2'b10: {out1} = 1'b1;\n"
                + "        2'b11: {out1} = 1'b0;\n"
                + "      endcase\n"
                + "    end\n"
                + "endmodule";
        NetSynth netsynth = new NetSynth();
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
   /*     {
  "outputs": [
    "y"
  ],
  "inputs": [
    "a",
    "b",
    "c"
  ],
  "netlist": [
    "NOT(0Wire44,b)",
    "NOT(0Wire45,c)",
    "NOR(n4,0Wire44,0Wire45)",
    "NOT(0Wire46,a)",
    "NOT(0Wire47,n4)",
    "NOR(y,0Wire46,0Wire47)"
  ],
  "collection": "motif_library"
}*/
        JSONObject motif = new JSONObject();
        JSONArray collection = new JSONArray();
        
        JSONArray outputsJ = new JSONArray();
        outputsJ.put("y");
        
        JSONArray inputsJ = new JSONArray();
        inputsJ.put("a");
        inputsJ.put("b");
        
        JSONArray netlistJ = new JSONArray();
        netlistJ.put("XOR(y,a,b)");
        
        try {
            motif.put("outputs", outputsJ);
            motif.put("inputs", inputsJ);
            motif.put("netlist", netlistJ);
            motif.put("collection", "motif_library");
            collection.put(motif);
        } catch (JSONException ex) {
            Logger.getLogger(NetSynthTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<DGate> netlist = netsynth.getNetlistCode(verilogLines, switches,collection);
        System.out.println("\n\nNETLIST ::");
        NetSynth.printNetlist(netlist);
        List<String> inputs = new ArrayList<String>();
        inputs.add("in1");
        inputs.add("in2");
        
        
        BooleanSimulator.printTruthTable(netlist, inputs);
        
        DAGW dag = netsynth.runNetSynthCode(verilogLines, switches, collection);
        System.out.println(dag.printGraph());
        
    }
    
    //@Test
    public void testGetNetlist(){
        String verilogLines = "module A(output out1, input in1, in2, in3);\n"
                + "  always@(in1,in2,in3)\n"
                + "    begin\n"
                + "      case({in1,in2,in3})\n"
                + "        3'b000: {out1} = 1'b1;\n"
                + "        3'b001: {out1} = 1'b1;\n"
                + "        3'b010: {out1} = 1'b1;\n"
                + "        3'b011: {out1} = 1'b1;\n"
                + "        3'b100: {out1} = 1'b1;\n"
                + "        3'b101: {out1} = 1'b1;\n"
                + "        3'b110: {out1} = 1'b1;\n"
                + "        3'b111: {out1} = 1'b0;\n"
                + "      endcase\n"
                + "    end\n"
                + "endmodule";
        NetSynth netsynth = new NetSynth();
        List<NetSynthSwitch> switches = new ArrayList<NetSynthSwitch>();
        List<DGate> netlist = netsynth.getNetlistCode(verilogLines, switches);
        System.out.println("\n\nNETLIST ::");
        NetSynth.printNetlist(netlist);
        List<String> inputs = new ArrayList<String>();
        inputs.add("in1");
        inputs.add("in2");
        inputs.add("in3");
        
        BooleanSimulator.printTruthTable(netlist, inputs);
        
    }
    
    public static List<DGate> getTestNetlist1(){
        
        //NOT(w1,inp1)
        //NOT(w2,inp2)
        //NOT(w3,w1,w2)
        //NOT(out,w3)
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire inp1 = new DWire("inp1",DWireType.input);
        DWire inp2 = new DWire("inp2",DWireType.input);
        
        DWire out = new DWire("out",DWireType.output);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        DWire w3 = new DWire("w3",DWireType.connector);
        
        DGate nor = new DGate();
        
        DGate not3 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not2.gtype = DGateType.NOT;
        not3.gtype = DGateType.NOT;
        nor.gtype = DGateType.NOR;
        
        not1.input.add(inp1);
        not2.input.add(inp2);
        
        not1.output = w1;
        not2.output = w2;
        
        nor.input.add(w1);
        nor.input.add(w2);
        nor.output = w3;
        
        not3.input.add(w3);
        not3.output = out;
        
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(nor);
        netlist.add(not3);
        
        return netlist;
    }
    
    public static List<DGate> getTestNetlist2(){
        
        //NOT(w1,input)
        //NOT(output,w1)
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire input = new DWire("inp",DWireType.input);
        DWire output = new DWire("out",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not1.input.add(input);
        not1.output = w1;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(w1);
        not2.output = output;
        
        
        netlist.add(not1);
        netlist.add(not2);
        
        return netlist;
    }
    
    public static List<DGate> getTestNetlist3(){
        
        //NOT(w1,input)
        //NOT(w2,w1)
        //NOT(output,w2)
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire input = new DWire("inp",DWireType.input);
        DWire output = new DWire("out",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        DGate not3 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not1.input.add(input);
        not1.output = w1;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(w1);
        not2.output = w2;
        
        not3.gtype = DGateType.NOT;
        not3.input.add(w2);
        not3.output = output;
        
        
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(not3);
        
        return netlist;
    }
    
    public static List<DGate> getTestNetlist4(){
        
        //NOT(w1,input)
        //NOT(w2,w1)
        //NOT(w3,w2)
        //NOT(output,w3)
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire input = new DWire("inp",DWireType.input);
        DWire output = new DWire("out",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        DWire w3 = new DWire("w3",DWireType.connector);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        DGate not3 = new DGate();
        DGate not4 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not1.input.add(input);
        not1.output = w1;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(w1);
        not2.output = w2;
        
        not3.gtype = DGateType.NOT;
        not3.input.add(w2);
        not3.output = w3;
        
        not4.gtype = DGateType.NOT;
        not4.input.add(w3);
        not4.output = output;
        
        
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(not3);
        netlist.add(not4);
        
        return netlist;
    }
    
    public static List<DGate> getTestNetlist5(){
        
        //NOT(w1,inp1)
        //NOT(out1,w1)
        //NOR(out2,inp2,out1)
        
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire inp1 = new DWire("inp1",DWireType.input);
        DWire inp2 = new DWire("inp2",DWireType.input);
        
        DWire out1 = new DWire("out1",DWireType.output);
        DWire out2 = new DWire("out2",DWireType.output);
        
        DWire w1 = new DWire("w1",DWireType.connector);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        DGate nor1 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not1.input.add(inp1);
        not1.output = w1;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(w1);
        not2.output = out1;
        
        nor1.gtype = DGateType.NOR;
        nor1.input.add(inp2);
        nor1.input.add(out1);
        nor1.output = out2;
        
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(nor1);
       
        return netlist;
    }
    
    public static List<DGate> getTestNetlist6(){
        //NOT(w0,in1)
        //NOT(w1,w0)
        //NOT(w2,in1)
        //NOR(out,w1,w2)
        List<DGate> netlist = new ArrayList<DGate>();
        
        DWire in1 = new DWire("in1",DWireType.input);
        DWire in2 = new DWire("in2",DWireType.input);
        
        DWire w0 = new DWire("w0",DWireType.connector);
        DWire w1 = new DWire("w1",DWireType.connector);
        DWire w2 = new DWire("w2",DWireType.connector);
        
        DWire out = new DWire("out",DWireType.output);
        
        DGate not1 = new DGate();
        DGate not2 = new DGate();
        DGate not3 = new DGate();
        
        DGate nor1 = new DGate();
        
        not1.gtype = DGateType.NOT;
        not1.input.add(in2);
        not1.output = w0;
        
        not2.gtype = DGateType.NOT;
        not2.input.add(w0);
        not2.output = w1;
        
        not3.gtype = DGateType.NOT;
        not3.input.add(in1);
        not3.output = w2;
        
        nor1.gtype = DGateType.NOR;
        nor1.input.add(w1);
        nor1.input.add(w2);
        nor1.output = out;
        
        netlist.add(not1);
        netlist.add(not2);
        netlist.add(not3);
        netlist.add(nor1);
        
        return netlist;
    }
}
