package utility_classes;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class FilteredWriter extends FilterWriter
{
    protected char[] filter = null;

    public FilteredWriter(Writer out) {
        super(out);
    }

    public void setFilter(String filteredChars) {
        filter = filteredChars.toCharArray();
    }

    public void write(String str, int off, int len) throws IOException
    {
        write(str.toCharArray(), off, len);
    }

    public void write(char[] cbuf, int off, int len) throws IOException
    {
        for (int i = off; i < off + len; i++)
            write(cbuf[i]);
    }

    public void write(int c) throws IOException
    {
        for (char f : filter)
        {
            if (f == (char)c)
                return;
        }
        out.write(c);
    }
}
