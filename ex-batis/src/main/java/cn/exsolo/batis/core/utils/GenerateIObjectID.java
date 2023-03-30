package cn.exsolo.batis.core.utils;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A microservices unique identifier for objects.
 *
 * @author prestolive
 * @date 2018/7/25.
 */
public class GenerateIObjectID implements Comparable<GenerateIObjectID>, Serializable {

    private final int _time;
    private final int _machine;
    private final int _inc;
    private boolean _new;
    private static final int _genmachine;

    private static AtomicInteger _nextInc = new AtomicInteger((new Random()).nextInt());

    private static final long serialVersionUID = 0L;

    private static final long TIME_OFFSET = (50L * 365 * 24 * 60 * 60);

    public GenerateIObjectID() {
        _time = (int) (System.currentTimeMillis() / 1000 - TIME_OFFSET);
        _machine = _genmachine;
        _inc = _nextInc.getAndIncrement();
        _new = true;
    }

    public String toHexString() {
        final StringBuilder buf = new StringBuilder(24);
        for (final byte b : toByteArray()) {
            buf.append(String.format("%02x", b & 0xff));
        }
        return buf.toString();
    }

    private byte[] toByteArray() {
        byte[] byteArr = new byte[12];
        ByteBuffer bb = ByteBuffer.wrap(byteArr);
        bb.putInt(_time);
        bb.putInt(_machine);
        bb.putInt(_inc);
        return byteArr;
    }

    private int _compareUnsigned(int i, int j) {
        long li = 0xFFFFFFFFL;
        li = i & li;
        long lj = 0xFFFFFFFFL;
        lj = j & lj;
        long diff = li - lj;
        if (diff < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (diff > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) diff;
    }

    @Override
    public int compareTo(GenerateIObjectID id) {
        if (id == null) {
            return -1;
        }
        int x = _compareUnsigned(_time, id._time);
        if (x != 0) {
            return x;
        }
        x = _compareUnsigned(_machine, id._machine);
        if (x != 0) {
            return x;
        }
        return _compareUnsigned(_inc, id._inc);
    }


    static {
        try {
            int machinePiece;
            {
                try {
                    StringBuilder sb = new StringBuilder();
                    Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                    while (e.hasMoreElements()) {
                        NetworkInterface ni = e.nextElement();
                        sb.append(ni.toString());
                    }
                    machinePiece = sb.toString().hashCode() << 16;
                } catch (Throwable e) {
                    machinePiece = (new Random().nextInt()) << 16;
                }
            }
            final int processPiece;
            {
                int processId = new Random().nextInt();
                try {
                    processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
                } catch (Throwable t) {
                }
                ClassLoader loader = GenerateIObjectID.class.getClassLoader();
                int loaderId = loader != null ? System.identityHashCode(loader) : 0;
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toHexString(processId));
                sb.append(Integer.toHexString(loaderId));
                processPiece = sb.toString().hashCode() & 0xFFFF;
            }
            _genmachine = machinePiece | processPiece;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}