package test;
public class test {
    public void test() {
        int i = 0;
        int j = 0;
        float v = 0;
        float x;
        float[] a = new float[100];
        while (true) {
            do i = i + 1; while (a[i] < v);
            do i = j - 1; while (a[j] > v);
            if (i >= j) break;
            x = a[i];
            a[i] = a[j];
            a[j] = x;
        }
    }
}
