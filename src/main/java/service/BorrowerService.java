package service;

import data.Borrower;

public interface BorrowerService {
    Borrower getBorrower(int id);
    boolean add(Borrower borrower);
}
