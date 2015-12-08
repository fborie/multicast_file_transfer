// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FileAnnouncement.proto

package cl.uandes.so.server;

public final class FileAnnouncementProtos {
  private FileAnnouncementProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface FileAnnouncementOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string checksum = 1;
    /**
     * <code>required string checksum = 1;</code>
     */
    boolean hasChecksum();
    /**
     * <code>required string checksum = 1;</code>
     */
    java.lang.String getChecksum();
    /**
     * <code>required string checksum = 1;</code>
     */
    com.google.protobuf.ByteString
        getChecksumBytes();

    // required string fileName = 2;
    /**
     * <code>required string fileName = 2;</code>
     */
    boolean hasFileName();
    /**
     * <code>required string fileName = 2;</code>
     */
    java.lang.String getFileName();
    /**
     * <code>required string fileName = 2;</code>
     */
    com.google.protobuf.ByteString
        getFileNameBytes();

    // required int32 fileSize = 3;
    /**
     * <code>required int32 fileSize = 3;</code>
     */
    boolean hasFileSize();
    /**
     * <code>required int32 fileSize = 3;</code>
     */
    int getFileSize();

    // required int32 chunksTotal = 4;
    /**
     * <code>required int32 chunksTotal = 4;</code>
     */
    boolean hasChunksTotal();
    /**
     * <code>required int32 chunksTotal = 4;</code>
     */
    int getChunksTotal();
  }
  /**
   * Protobuf type {@code so.FileAnnouncement}
   */
  public static final class FileAnnouncement extends
      com.google.protobuf.GeneratedMessage
      implements FileAnnouncementOrBuilder {
    // Use FileAnnouncement.newBuilder() to construct.
    private FileAnnouncement(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private FileAnnouncement(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final FileAnnouncement defaultInstance;
    public static FileAnnouncement getDefaultInstance() {
      return defaultInstance;
    }

    public FileAnnouncement getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private FileAnnouncement(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              checksum_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              fileName_ = input.readBytes();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              fileSize_ = input.readInt32();
              break;
            }
            case 32: {
              bitField0_ |= 0x00000008;
              chunksTotal_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cl.uandes.so.server.FileAnnouncementProtos.internal_static_so_FileAnnouncement_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cl.uandes.so.server.FileAnnouncementProtos.internal_static_so_FileAnnouncement_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.class, cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.Builder.class);
    }

    public static com.google.protobuf.Parser<FileAnnouncement> PARSER =
        new com.google.protobuf.AbstractParser<FileAnnouncement>() {
      public FileAnnouncement parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new FileAnnouncement(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<FileAnnouncement> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string checksum = 1;
    public static final int CHECKSUM_FIELD_NUMBER = 1;
    private java.lang.Object checksum_;
    /**
     * <code>required string checksum = 1;</code>
     */
    public boolean hasChecksum() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string checksum = 1;</code>
     */
    public java.lang.String getChecksum() {
      java.lang.Object ref = checksum_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          checksum_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string checksum = 1;</code>
     */
    public com.google.protobuf.ByteString
        getChecksumBytes() {
      java.lang.Object ref = checksum_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        checksum_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required string fileName = 2;
    public static final int FILENAME_FIELD_NUMBER = 2;
    private java.lang.Object fileName_;
    /**
     * <code>required string fileName = 2;</code>
     */
    public boolean hasFileName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string fileName = 2;</code>
     */
    public java.lang.String getFileName() {
      java.lang.Object ref = fileName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          fileName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string fileName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getFileNameBytes() {
      java.lang.Object ref = fileName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        fileName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required int32 fileSize = 3;
    public static final int FILESIZE_FIELD_NUMBER = 3;
    private int fileSize_;
    /**
     * <code>required int32 fileSize = 3;</code>
     */
    public boolean hasFileSize() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 fileSize = 3;</code>
     */
    public int getFileSize() {
      return fileSize_;
    }

    // required int32 chunksTotal = 4;
    public static final int CHUNKSTOTAL_FIELD_NUMBER = 4;
    private int chunksTotal_;
    /**
     * <code>required int32 chunksTotal = 4;</code>
     */
    public boolean hasChunksTotal() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int32 chunksTotal = 4;</code>
     */
    public int getChunksTotal() {
      return chunksTotal_;
    }

    private void initFields() {
      checksum_ = "";
      fileName_ = "";
      fileSize_ = 0;
      chunksTotal_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasChecksum()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasFileName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasFileSize()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasChunksTotal()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getChecksumBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getFileNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, fileSize_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeInt32(4, chunksTotal_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getChecksumBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getFileNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, fileSize_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, chunksTotal_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code so.FileAnnouncement}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncementOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return cl.uandes.so.server.FileAnnouncementProtos.internal_static_so_FileAnnouncement_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return cl.uandes.so.server.FileAnnouncementProtos.internal_static_so_FileAnnouncement_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.class, cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.Builder.class);
      }

      // Construct using cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        checksum_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        fileName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        fileSize_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        chunksTotal_ = 0;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return cl.uandes.so.server.FileAnnouncementProtos.internal_static_so_FileAnnouncement_descriptor;
      }

      public cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement getDefaultInstanceForType() {
        return cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.getDefaultInstance();
      }

      public cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement build() {
        cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement buildPartial() {
        cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement result = new cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.checksum_ = checksum_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.fileName_ = fileName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.fileSize_ = fileSize_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.chunksTotal_ = chunksTotal_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement) {
          return mergeFrom((cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement other) {
        if (other == cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement.getDefaultInstance()) return this;
        if (other.hasChecksum()) {
          bitField0_ |= 0x00000001;
          checksum_ = other.checksum_;
          onChanged();
        }
        if (other.hasFileName()) {
          bitField0_ |= 0x00000002;
          fileName_ = other.fileName_;
          onChanged();
        }
        if (other.hasFileSize()) {
          setFileSize(other.getFileSize());
        }
        if (other.hasChunksTotal()) {
          setChunksTotal(other.getChunksTotal());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasChecksum()) {
          
          return false;
        }
        if (!hasFileName()) {
          
          return false;
        }
        if (!hasFileSize()) {
          
          return false;
        }
        if (!hasChunksTotal()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (cl.uandes.so.server.FileAnnouncementProtos.FileAnnouncement) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string checksum = 1;
      private java.lang.Object checksum_ = "";
      /**
       * <code>required string checksum = 1;</code>
       */
      public boolean hasChecksum() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string checksum = 1;</code>
       */
      public java.lang.String getChecksum() {
        java.lang.Object ref = checksum_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          checksum_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string checksum = 1;</code>
       */
      public com.google.protobuf.ByteString
          getChecksumBytes() {
        java.lang.Object ref = checksum_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          checksum_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string checksum = 1;</code>
       */
      public Builder setChecksum(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        checksum_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string checksum = 1;</code>
       */
      public Builder clearChecksum() {
        bitField0_ = (bitField0_ & ~0x00000001);
        checksum_ = getDefaultInstance().getChecksum();
        onChanged();
        return this;
      }
      /**
       * <code>required string checksum = 1;</code>
       */
      public Builder setChecksumBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        checksum_ = value;
        onChanged();
        return this;
      }

      // required string fileName = 2;
      private java.lang.Object fileName_ = "";
      /**
       * <code>required string fileName = 2;</code>
       */
      public boolean hasFileName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string fileName = 2;</code>
       */
      public java.lang.String getFileName() {
        java.lang.Object ref = fileName_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          fileName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string fileName = 2;</code>
       */
      public com.google.protobuf.ByteString
          getFileNameBytes() {
        java.lang.Object ref = fileName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          fileName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string fileName = 2;</code>
       */
      public Builder setFileName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        fileName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string fileName = 2;</code>
       */
      public Builder clearFileName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        fileName_ = getDefaultInstance().getFileName();
        onChanged();
        return this;
      }
      /**
       * <code>required string fileName = 2;</code>
       */
      public Builder setFileNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        fileName_ = value;
        onChanged();
        return this;
      }

      // required int32 fileSize = 3;
      private int fileSize_ ;
      /**
       * <code>required int32 fileSize = 3;</code>
       */
      public boolean hasFileSize() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required int32 fileSize = 3;</code>
       */
      public int getFileSize() {
        return fileSize_;
      }
      /**
       * <code>required int32 fileSize = 3;</code>
       */
      public Builder setFileSize(int value) {
        bitField0_ |= 0x00000004;
        fileSize_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 fileSize = 3;</code>
       */
      public Builder clearFileSize() {
        bitField0_ = (bitField0_ & ~0x00000004);
        fileSize_ = 0;
        onChanged();
        return this;
      }

      // required int32 chunksTotal = 4;
      private int chunksTotal_ ;
      /**
       * <code>required int32 chunksTotal = 4;</code>
       */
      public boolean hasChunksTotal() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required int32 chunksTotal = 4;</code>
       */
      public int getChunksTotal() {
        return chunksTotal_;
      }
      /**
       * <code>required int32 chunksTotal = 4;</code>
       */
      public Builder setChunksTotal(int value) {
        bitField0_ |= 0x00000008;
        chunksTotal_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 chunksTotal = 4;</code>
       */
      public Builder clearChunksTotal() {
        bitField0_ = (bitField0_ & ~0x00000008);
        chunksTotal_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:so.FileAnnouncement)
    }

    static {
      defaultInstance = new FileAnnouncement(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:so.FileAnnouncement)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_so_FileAnnouncement_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_so_FileAnnouncement_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026FileAnnouncement.proto\022\002so\"]\n\020FileAnno" +
      "uncement\022\020\n\010checksum\030\001 \002(\t\022\020\n\010fileName\030\002" +
      " \002(\t\022\020\n\010fileSize\030\003 \002(\005\022\023\n\013chunksTotal\030\004 " +
      "\002(\005B-\n\023cl.uandes.so.serverB\026FileAnnounce" +
      "mentProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_so_FileAnnouncement_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_so_FileAnnouncement_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_so_FileAnnouncement_descriptor,
              new java.lang.String[] { "Checksum", "FileName", "FileSize", "ChunksTotal", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}