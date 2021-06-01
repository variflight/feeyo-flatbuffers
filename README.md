# feeyo-flatbuffers
An extension based on Google flatbuffers

## Usage

```
  <dependency>
	<groupId>com.github.variflight</groupId>
	<artifactId>feeyo-flatbuffers</artifactId>
	<version>0.1.0</version>
  </dependency>
	
	
	public static class User extends BaseModel {
		private int id;
		private String name;
		
		public User(){}
	
		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}
		public int getId() {
			return getInt(0);
		}
		public String getName() {
			return getString(1);
		}
		@Override
		public int flattenToBuffer(FlatBufferBuilder flatBufferBuilder) {
			int nameOffset = flatBufferBuilder.createString(name);
			flatBufferBuilder.startTable(2);
			flatBufferBuilder.addInt(0, id, 0);
			flatBufferBuilder.addOffset(1, nameOffset, 0);
			int end = flatBufferBuilder.endTable();
			flatBufferBuilder.finish(end);
			return end;
		}
	}
	
	...

	FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(128);
	List<User> userList = new ArrayList<>();
	userList.add( new User(1, "x1") );
	userList.add( new User(2, "x2") );
	userList.add( new User(3, "x3") );
	userList.add( new User(4, "x4") );
	userList.add( new User(5, "x5") );
	userList.add( new User(6, "x6") );
	FlattenableHelper.flattenToBuffer(flatBufferBuilder, userList);
	ByteBuffer buffer = flatBufferBuilder.dataBuffer();
	
	byte[] bb = new byte[ buffer.capacity() - buffer.position() ];
	for(int i=0; i< bb.length; i++) {
		bb[i] = buffer.get();
	}
	System.out.println(ByteUtil.dump(bb, 0, bb.length));

```
