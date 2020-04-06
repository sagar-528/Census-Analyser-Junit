package censusanalyser;

import java.util.Iterator;

public class CsvIterableImpl implements Iterable{
    Iterator iterator;

    public CsvIterableImpl(Iterator iterator)
    {
        this.iterator = iterator;
    }

    @Override
    public Iterator iterator()
    {
        return iterator;
    }
}
