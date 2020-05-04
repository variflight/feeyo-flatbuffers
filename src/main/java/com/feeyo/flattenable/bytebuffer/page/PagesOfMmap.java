package com.feeyo.flattenable.bytebuffer.page;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.feeyo.flattenable.util.MappedByteBufferUtil;

/**
 * 基于文件映射
 * 
 * @see https://manybutfinite.com/post/page-cache-the-affair-between-memory-and-files/
 * 
 * @author zhuam
 *
 */
public class PagesOfMmap extends Pages {
	//	
    // 当前JVM中映射的虚拟内存总大小
    private static final AtomicLong TOTAL_MAPPED_VIRTUAL_MEMORY = new AtomicLong(0);
    //
    // 当前JVM中mmap句柄数量
    private static final AtomicInteger TOTAL_MAPPED_FILES = new AtomicInteger(0);
	
	//
	private String basePath;
	private LinkedList<MappedFile> mmapfiles = new LinkedList<>();

	//
	public PagesOfMmap(String path, long capacity, int chunkSize) {
		super(capacity, chunkSize);
		this.basePath = path;
	}

	@Override
	public synchronized void initialize() {
    	//
		try {
	    	this.allPageBuffers = new ByteBufferPage[pageCount];
	    	for (int i = 0; i < pageCount; i++) {
	    		String fileName = basePath + File.separator + i + ".tmp";
	    		MappedFile f = new MappedFile(fileName, pageSize);
	    		mmapfiles.add(f);
	    		//
	    		allPageBuffers[i] = new ByteBufferPage(f.mappedByteBuffer, chunkSize);
	    	}
	        //
	      	this.prevAllocatedPage = new AtomicInteger(0);
	      	//
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void destroy() {
		for (MappedFile f: mmapfiles) 
			f.close();
		mmapfiles.clear();
		mmapfiles = null;
	}
	
	
    public static int getTotalMappedFiles() {
        return TOTAL_MAPPED_FILES.get();
    }

    public static long getTotalMappedVirtualMemory() {
        return TOTAL_MAPPED_VIRTUAL_MEMORY.get();
    }
	
	///
	//
	private static class MappedFile {
		//
		private File file;
		private RandomAccessFile raf;
		private MappedByteBuffer mappedByteBuffer;
		private int fileSize;
		
		public MappedFile(String fileName, int size) throws IOException {
			this.file = new File( fileName );
    		if ( !file.exists() )
    			file.createNewFile();
    		else {
    			if ( !file.delete() )
    				throw new IOException("file exists err:" + fileName);
    		}
    		//
    		this.fileSize = size;
    		this.raf = new RandomAccessFile(file, "rw");
    		this.mappedByteBuffer = this.raf.getChannel().map(MapMode.READ_WRITE, 0, fileSize);
    		//
    		TOTAL_MAPPED_VIRTUAL_MEMORY.addAndGet(fileSize);
            TOTAL_MAPPED_FILES.incrementAndGet();
		}
		
		public void close() {
			if ( mappedByteBuffer != null ) 
				MappedByteBufferUtil.closeDirectBuffer( mappedByteBuffer );
			close( raf );
			if ( !file.delete() )
				throw new RuntimeException("Unable to delete file: " + file);
			//
			TOTAL_MAPPED_VIRTUAL_MEMORY.addAndGet(this.fileSize * (-1));
			TOTAL_MAPPED_FILES.decrementAndGet();
		}
		//
		private void close(Closeable closeable) {
			try {
				if (closeable != null)
					closeable.close();
			} catch (Exception e) {
				//
			}
		}
	}
}