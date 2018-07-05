package dataStruct;

   public class Point3D {
            /**
             * x coordinate.
             */
            public double x;
            /**
             * y coordinate.
             */
            public double y;
            /**
             * z coordinate.
             */
            
            public double z;

            /**
             * Constructor for the Point3D object
             */
            public Point3D() {
            }

            /**
             * Constructor for the Point3D object
             *
             * @param x x coordinate.
             * @param y y coordinate.
             * @param z z coordinate.
             */
            public Point3D(float x, float y, float z) {
                this .x = x;
                this .y = y;
                this .z = z;
            }
            public Point3D(double x, double y, double z) {
                this .x = x;
                this .y = y;
                this .z = z;
            }
            public Point3D(int x,int y, int z) {
                this .x = x;
                this .y = y;
                this .z = z;
            }
            public Point3D(double[] pos) {
                this .x = pos[0];
                this .y = pos[1];
                this .z = pos[2];
            }
            public Point3D(float[] pos) {
                this .x = pos[0];
                this .y = pos[1];
                this .z = pos[2];
            }
            public Point3D(int[] pos) {
                this .x = pos[0];
                this .y = pos[1];
                this .z = pos[2];
            }
          
            
            

            /**
             * Returns a string representing Point3D coordinates: (x,y,z).
             *
             * @return String representing Point3D coordinates.
             */
            @Override
            public String toString() {
            	FormatterLab formatter = new FormatterLab(4,2);
                return "(" + formatter.format(x) + "," + formatter.format(y) + "," + formatter.format(z) + ")";
            }
        }