package LeoLibraries.LeoLibraries;

public class Threading  {


    public static void main(String[] args) {

        Runnable a = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++)
                {
                    System.out.println("HELLO BIT " + i);
                }
            }
        };


        Runnable b = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++)
                {
                    System.out.println("SUP BIT " + i);
                }
            }
        };

        Runnable c = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++)
                {
                    System.out.println("N BIT " + i);
                }
            }
        };



        Thread at = new Thread(a);
        Thread bt = new Thread(b);
        Thread ct = new Thread(c);

        at.start();
        bt.start();
        ct.start();





    }
}