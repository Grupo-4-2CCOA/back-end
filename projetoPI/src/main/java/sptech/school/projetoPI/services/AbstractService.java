package sptech.school.projetoPI.services;

import java.util.List;

public abstract class AbstractService<Entity> {
    public abstract Entity postMethod(Entity entity);
    public abstract List<Entity> getAllMethod();
    public abstract Entity getByIdMethod(Integer id);
    public abstract Entity putByIdMethod(Entity entity, Integer id);
    public abstract void deleteByIdMethod(Integer id);
}
