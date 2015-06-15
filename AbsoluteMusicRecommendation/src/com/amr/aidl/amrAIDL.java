/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\������\\git\\MobileCloud\\Absolute-Music-Recommendation\\AbsoluteMusicRecommendation\\src\\com\\amr\\aidl\\amrAIDL.aidl
 */
package com.amr.aidl;
public interface amrAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.amr.aidl.amrAIDL
{
private static final java.lang.String DESCRIPTOR = "com.amr.aidl.amrAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.amr.aidl.amrAIDL interface,
 * generating a proxy if needed.
 */
public static com.amr.aidl.amrAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.amr.aidl.amrAIDL))) {
return ((com.amr.aidl.amrAIDL)iin);
}
return new com.amr.aidl.amrAIDL.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getKeywordToRecommendLists:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String uri;
uri = data.readString();
java.lang.String artist;
artist = data.readString();
java.lang.String title;
title = data.readString();
int count;
count = data.readInt();
this.getKeywordToRecommendLists(recvAction, uri, artist, title, count);
reply.writeNoException();
return true;
}
case TRANSACTION_setUserRegistered:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String userID;
userID = data.readString();
this.setUserRegistered(recvAction, userID);
reply.writeNoException();
return true;
}
case TRANSACTION_setUserUnregistered:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String userID;
userID = data.readString();
this.setUserUnregistered(recvAction, userID);
reply.writeNoException();
return true;
}
case TRANSACTION_setUserPlay:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String uri;
uri = data.readString();
java.lang.String userID;
userID = data.readString();
java.lang.String artist;
artist = data.readString();
java.lang.String title;
title = data.readString();
java.lang.String album;
album = data.readString();
this.setUserPlay(recvAction, uri, userID, artist, title, album);
reply.writeNoException();
return true;
}
case TRANSACTION_getMusicViewUserList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String trackID;
trackID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getMusicViewUserList(recvAction, trackID, start, count);
reply.writeNoException();
return true;
}
case TRANSACTION_getMatchedViewList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String reqUserID;
reqUserID = data.readString();
java.lang.String lookUpUserID;
lookUpUserID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getMatchedViewList(recvAction, reqUserID, lookUpUserID, start, count);
reply.writeNoException();
return true;
}
case TRANSACTION_getUnmatchedViewList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String reqUserID;
reqUserID = data.readString();
java.lang.String lookUpUserID;
lookUpUserID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getUnmatchedViewList(recvAction, reqUserID, lookUpUserID, start, count);
reply.writeNoException();
return true;
}
case TRANSACTION_setMate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String matingUserID;
matingUserID = data.readString();
java.lang.String matedUserID;
matedUserID = data.readString();
this.setMate(recvAction, matingUserID, matedUserID);
reply.writeNoException();
return true;
}
case TRANSACTION_setUnmate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String matingUserID;
matingUserID = data.readString();
java.lang.String matedUserID;
matedUserID = data.readString();
this.setUnmate(recvAction, matingUserID, matedUserID);
reply.writeNoException();
return true;
}
case TRANSACTION_reviewWrite:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String userID;
userID = data.readString();
java.lang.String trackID;
trackID = data.readString();
java.lang.String content;
content = data.readString();
this.reviewWrite(recvAction, userID, trackID, content);
reply.writeNoException();
return true;
}
case TRANSACTION_getMusicReview:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String trackID;
trackID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getMusicReview(recvAction, trackID, start, count);
reply.writeNoException();
return true;
}
case TRANSACTION_getUserReview:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String userID;
userID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getUserReview(recvAction, userID, start, count);
reply.writeNoException();
return true;
}
case TRANSACTION_getUserMatesViewList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String recvAction;
recvAction = data.readString();
java.lang.String userID;
userID = data.readString();
int start;
start = data.readInt();
int count;
count = data.readInt();
this.getUserMatesViewList(recvAction, userID, start, count);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.amr.aidl.amrAIDL
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void getKeywordToRecommendLists(java.lang.String recvAction, java.lang.String uri, java.lang.String artist, java.lang.String title, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(uri);
_data.writeString(artist);
_data.writeString(title);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getKeywordToRecommendLists, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUserRegistered(java.lang.String recvAction, java.lang.String userID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(userID);
mRemote.transact(Stub.TRANSACTION_setUserRegistered, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUserUnregistered(java.lang.String recvAction, java.lang.String userID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(userID);
mRemote.transact(Stub.TRANSACTION_setUserUnregistered, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUserPlay(java.lang.String recvAction, java.lang.String uri, java.lang.String userID, java.lang.String artist, java.lang.String title, java.lang.String album) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(uri);
_data.writeString(userID);
_data.writeString(artist);
_data.writeString(title);
_data.writeString(album);
mRemote.transact(Stub.TRANSACTION_setUserPlay, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getMusicViewUserList(java.lang.String recvAction, java.lang.String trackID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(trackID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getMusicViewUserList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getMatchedViewList(java.lang.String recvAction, java.lang.String reqUserID, java.lang.String lookUpUserID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(reqUserID);
_data.writeString(lookUpUserID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getMatchedViewList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getUnmatchedViewList(java.lang.String recvAction, java.lang.String reqUserID, java.lang.String lookUpUserID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(reqUserID);
_data.writeString(lookUpUserID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getUnmatchedViewList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setMate(java.lang.String recvAction, java.lang.String matingUserID, java.lang.String matedUserID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(matingUserID);
_data.writeString(matedUserID);
mRemote.transact(Stub.TRANSACTION_setMate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setUnmate(java.lang.String recvAction, java.lang.String unmatingUser_id, java.lang.String unmatedUserID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(unmatingUser_id);
_data.writeString(unmatedUserID);
mRemote.transact(Stub.TRANSACTION_setUnmate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void reviewWrite(java.lang.String recvAction, java.lang.String userID, java.lang.String trackID, java.lang.String content) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(userID);
_data.writeString(trackID);
_data.writeString(content);
mRemote.transact(Stub.TRANSACTION_reviewWrite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getMusicReview(java.lang.String recvAction, java.lang.String trackID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(trackID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getMusicReview, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getUserReview(java.lang.String recvAction, java.lang.String userID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(userID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getUserReview, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getUserMatesViewList(java.lang.String recvAction, java.lang.String userID, int start, int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(recvAction);
_data.writeString(userID);
_data.writeInt(start);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_getUserMatesViewList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getKeywordToRecommendLists = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setUserRegistered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setUserUnregistered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setUserPlay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getMusicViewUserList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getMatchedViewList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getUnmatchedViewList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setMate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setUnmate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_reviewWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getMusicReview = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getUserReview = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getUserMatesViewList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public void getKeywordToRecommendLists(java.lang.String recvAction, java.lang.String uri, java.lang.String artist, java.lang.String title, int count) throws android.os.RemoteException;
public void setUserRegistered(java.lang.String recvAction, java.lang.String userID) throws android.os.RemoteException;
public void setUserUnregistered(java.lang.String recvAction, java.lang.String userID) throws android.os.RemoteException;
public void setUserPlay(java.lang.String recvAction, java.lang.String uri, java.lang.String userID, java.lang.String artist, java.lang.String title, java.lang.String album) throws android.os.RemoteException;
public void getMusicViewUserList(java.lang.String recvAction, java.lang.String trackID, int start, int count) throws android.os.RemoteException;
public void getMatchedViewList(java.lang.String recvAction, java.lang.String reqUserID, java.lang.String lookUpUserID, int start, int count) throws android.os.RemoteException;
public void getUnmatchedViewList(java.lang.String recvAction, java.lang.String reqUserID, java.lang.String lookUpUserID, int start, int count) throws android.os.RemoteException;
public void setMate(java.lang.String recvAction, java.lang.String matingUserID, java.lang.String matedUserID) throws android.os.RemoteException;
public void setUnmate(java.lang.String recvAction, java.lang.String unmatingUser_id, java.lang.String unmatedUserID) throws android.os.RemoteException;
public void reviewWrite(java.lang.String recvAction, java.lang.String userID, java.lang.String trackID, java.lang.String content) throws android.os.RemoteException;
public void getMusicReview(java.lang.String recvAction, java.lang.String trackID, int start, int count) throws android.os.RemoteException;
public void getUserReview(java.lang.String recvAction, java.lang.String userID, int start, int count) throws android.os.RemoteException;
public void getUserMatesViewList(java.lang.String recvAction, java.lang.String userID, int start, int count) throws android.os.RemoteException;
}
