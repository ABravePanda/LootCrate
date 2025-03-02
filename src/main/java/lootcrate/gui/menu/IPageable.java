package lootcrate.gui.menu;

import lootcrate.gui.item.MenuItem;

import java.util.List;

public interface IPageable {

    public int getPage();
    public void setPage(int page);

    public List<MenuItem> getPageItems();
    public void nextPage();
    public void previousPage();
}
