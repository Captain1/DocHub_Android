package me.android.dochub.utils;
import android.util.Log;
import java.security.MessageDigest;

public class MD5Digest {
   public static void main(String[] args){}
   public String encode(String pt) throws Exception {
      String original = pt;
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(original.getBytes());
      byte[] digest = md.digest();
      StringBuffer sb = new StringBuffer();
      for (byte b : digest) {
         sb.append(String.format("%02x", b & 0xff));
      }
      Log.d("original:", original);
      Log.d("digested(hex):", sb.toString());
      return sb.toString();
   }
}
