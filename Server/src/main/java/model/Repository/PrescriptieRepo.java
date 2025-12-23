package model.Repository;
import model.*;
import java.util.*;
public interface PrescriptieRepo {
    void salvare(Prescriptie p);
    List<Prescriptie> findByPacientId(Long id);
    Prescriptie findById(Long id);
}