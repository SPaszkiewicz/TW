package lab7.jscp_b;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    One2OneChannelInt[] in;
    One2OneChannelInt[] req;
    One2OneChannelInt[] out;
    private int[] buffer = new int[10];
    int hd = -1;
    int tl = -1;
    public Buffer (final One2OneChannelInt[] in, final
    One2OneChannelInt[] req, final One2OneChannelInt[] out) {
        this.in = in;
        this.req = req;
        this.out = out;
    } // constructor
    public void run ()
    {
        final Guard[] guards = {in[0].in(), in[1].in(), req[0].in(), req[1].in()};
        final Alternative alt = new Alternative(guards);
        int countdown = 4; // Number of processes running
        while (countdown > 0) {
            int index = alt.select();
            switch (index) {
                case 0:
                case 1: // A Producer is ready to send
                    if (hd < tl + 11) {
                        int item = in[index].in().read();
                        if (item < 0)
                            countdown--;
                        else
                        { hd++;
                            buffer[hd%buffer.length] = item;
                        }
                    }
                    break;
                case 2:
                case 3:
                    if (tl < hd) {
                        req[index-2].in().read();
                        tl++;
                        int item = buffer[tl%buffer.length];
                        out[index-2].out().write(item);
                    }
                    else if (countdown <= 2) {
                    req[index-2].in().read();
                    out[index-2].out().write(-1); // Signal end
                    countdown--;
                }
                break;
            }
        }
        System.out.println("Buffer ended.");
    }
}