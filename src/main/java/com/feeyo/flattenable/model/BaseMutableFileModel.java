package com.feeyo.flattenable.model;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import com.google.flatbuffers.FlatBufferBuilder;

//
public abstract class BaseMutableFileModel extends BaseMutableModel {
	
	//
	public int flattenToFile(FlatBufferBuilder flatBufferBuilder, String fileName) throws IOException {
		//
		File file = new File( fileName );
		if ( file.exists() )
			throw new IOException("file exists err:" + fileName);
		else
			file.createNewFile();
		//
		RandomAccessFile raf = null;
		FileChannel fc = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			fc = raf.getChannel();
			fc.write( flatBufferBuilder.dataBuffer() );
			fc.force(true);
			return (int) fc.position();
		} finally {
			this.closeQuietly( raf );
			this.closeQuietly( fc );
		}
	}
	
	//
	// closeQuietly
	protected void closeQuietly(Closeable c) {
		if ( c == null )
			return;
		try { c.close(); c = null; } catch(Exception e) {}
	}
	
	//
	//
	public static class FlatBufferFileLoader {

		private FileInputStream fis = null;
		private String fileName = null;
		
		//
		public MappedByteBuffer build(String fileName, int position, int size) throws IOException {
			//
			if (this.fis == null || !fileName.equalsIgnoreCase(this.fileName)) {
				//
				if (this.fis != null) {
					try {
						this.fis.close();
						this.fis = null;
					} catch (IOException e) { /* ignored */ }
				}
				// 
				// 
				this.fis = new FileInputStream(fileName);
			}
			// 
			if ( this.fis == null )
				throw new IOException("Couldn't build stream for " + fileName);
			//
			this.fileName = fileName;
			return this.fis.getChannel().map(MapMode.READ_ONLY, (long) position, (long) size);
		}

		//
		public void close() {
			//
			if (this.fis != null) {
				try {
					this.fis.close();
					this.fis = null;
				} catch (IOException e) { /* ignored */ }
			}
		}
	}
}
