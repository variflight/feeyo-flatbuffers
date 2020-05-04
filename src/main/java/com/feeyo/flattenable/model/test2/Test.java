package com.feeyo.flattenable.model.test2;


import com.feeyo.flattenable.FlattenableMap;
import com.google.flatbuffers.FlatBufferBuilder;

import java.util.HashMap;
import java.util.Map;

public class Test {
    //
    public static void main(String[] args) {
        testFlatVertex();
        testFlatEdge();
        testFlatVertexList();
        testFlatEdgeList();
        testMessage();
        testMap();
    }

    private static void testMessage() {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        // int[] offsets = new int[2];
        // for (int i = 0; i < 4; i++) {
        //
        //     offsets[i] = Entry.createEntry(flatBufferBuilder, 1 + i, 2 + i, ("Entry" + i).getBytes());
        // }
        // EntryList entries = new EntryList();
        // entries.initFromFlatBufferBuilder(flatBufferBuilder, offsets);
        //
        // Message message = new Message();
        // Message.createMessage(flatBufferBuilder, 101, 103, entries);
        // message.initFromByteBuffer(flatBufferBuilder.dataBuffer());
        //
        // System.out.println(new String(message.getEntryList().get(0).getData()));

        Entry entry0 = new Entry();
        entry0.setIndex(10);
        entry0.setTerm(20);
        entry0.setData("Entry0000".getBytes());

        Message message0 = new Message();
        message0.setFrom(101);
        message0.setTo(103);
        message0.setEntry(entry0);
        message0.setTerm(20);
        message0.flattenToBuffer(flatBufferBuilder);

        Message message = new Message();
        message.initFromByteBuffer(flatBufferBuilder.dataBuffer());

        System.out.println("message from: " + message.getFrom() + "\tmessage to: " + message.getTo() + "\tterm: " + message.getTerm());
        Entry entry = message.getEntry();
        System.out.println("entry index: " + entry.getIndex() + "\tentry term: " + entry.getTerm() + "\tdata: " + new String(entry.getData()));
        System.out.println();
    }

    private static void testFlatVertex() {
        FlatVertex flatVertex = new FlatVertex();
        flatVertex.setId(1);
        flatVertex.setName("合肥南");
        flatVertex.setCode("341000");
        flatVertex.setNamePy("HeFeiNan");
        flatVertex.setBelong("100");
        flatVertex.setLat(100.0D);
        flatVertex.setLng(95.878D);
        flatVertex.setBizCode(12);

        System.out.println(flatVertex.toJSONString());

        FlatVertex flatVertex2 = new FlatVertex();
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        flatVertex.flattenToBuffer(flatBufferBuilder);
        //
        flatVertex2.initFromByteBuffer(flatBufferBuilder.dataBuffer());
        System.out.println(flatVertex2.toJSONString());
        System.out.println();
    }


    private static void testFlatEdge() {
        FlatEdge flatEdge = new FlatEdge();
        flatEdge.setId(100);
        flatEdge.setStartVertex(10);
        flatEdge.setEndVertex(20);
        flatEdge.setDepTime(100000);
        flatEdge.setArrTime(200000);
        flatEdge.setDistance(95.32D);
        flatEdge.setOrg(2500);
        flatEdge.setDst(1500);
        flatEdge.setEdgeType(8);
        flatEdge.setNumOffset(2);
        flatEdge.setType(1);
        System.out.println(flatEdge.toJSONString());

        FlatEdge flatEdge2 = new FlatEdge();
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        flatEdge.flattenToBuffer(flatBufferBuilder);
        flatEdge2.initFromByteBuffer(flatBufferBuilder.dataBuffer());
        System.out.println(flatEdge2.toJSONString());
        System.out.println();
    }

    private static void testFlatVertexList() {
        int[] offsets = new int[4];
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        for (int i = 0; i < 4; i++) {

            offsets[i] = FlatVertex.createFlatVertex(flatBufferBuilder, 1 + i, "合肥南" + i, "341000" + i, //
                    "HeFeiNan" + i, 100.0D + i, 95.878D + i, 12 + i, "100" + i);
        }

        FlatVertexList flatVertexList = new FlatVertexList(FlatVertex.class);
        flatVertexList.initFromFlatBufferBuilder(flatBufferBuilder, offsets);
        System.out.println("List size is " + flatVertexList.size() + "\t" + flatVertexList.get(2).toJSONString());
        System.out.println();
    }

    private static void testFlatEdgeList() {
        int[] offsets = new int[4];
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        for (int i = 0; i < 4; i++) {

            offsets[i] = FlatEdge.createFlatEdge(flatBufferBuilder, 100 + i, 10 + i, 20 + i, 100000 + i, //
                    200000 + i, 1 + i, 2500 + i, 1500 + i, 2 + i, 95.32D + i, 8 + i);
        }

        FlatEdgeList flatEdgeList = new FlatEdgeList(FlatEdge.class);
        flatEdgeList.initFromFlatBufferBuilder(flatBufferBuilder, offsets);
        System.out.println("List size is " + flatEdgeList.size() + "\t" + flatEdgeList.get(2).toJSONString());
        System.out.println();
    }

    private static void testMap() {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(2048);
        Map<String, Error> map = new HashMap<>();
        Error error0 = new Error();
        error0.setErrorCode(100);
        error0.setErrorMessage("Sorry");
        map.put("AAA", error0);

        Error error1 = new Error();
        error1.setErrorCode(200);
        error1.setErrorMessage("No");
        map.put("BBB", error1);
        
        
        FlattenableMap<Error> modelMap = new FlattenableMap<>( Error.class );
        modelMap.initFromFlatBufferBuilder(flatBufferBuilder, map);
        System.out.println( modelMap.size() );
        System.out.println( modelMap.get("AAA").getErrorMessage() );
      
    }
}