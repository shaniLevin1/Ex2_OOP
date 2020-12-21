package api;

public class geoLocation implements geo_location{
    private double x,y,z;

    /**
     * returns x value of required location
     * @return x value of required location
     */
    @Override
    public double x() {
        return x;
    }

    /**
     * returns y value of required location
     * @return y value of required location
     */
    @Override
    public double y() {
        return y;
    }

    /**
     * returns z value of required location
     * @return z value of required location
     */
    @Override
    public double z() {
        return z;
    }

    /**
     * returns the distance between a point from required location
     * @param g the point to return the distance with
     * @return the distance between a point from required location
     */
    @Override
    public double distance(geo_location g) {

        if (g==null||g==this||(g.x()==this.x&&g.y()==this.y&&g.z()==this.z))
            return 0;
        return Math.sqrt((Math.pow(this.x-g.x(),2))+(Math.pow(this.y-g.y(),2))+(Math.pow(this.z-g.z(),2)));
    }
}