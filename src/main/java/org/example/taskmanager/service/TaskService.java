package org.example.taskmanager.service;

import org.example.taskmanager.entity.Task;
import org.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {

        return taskRepository.findAll();//select * from sorgusunun kod karşılığıdır.
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);//spesifik görevi bulmaya yarar(id).select * from where id sorgusunun metodu
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);}
    //eğer id yoksa id ekler varsa bilgi güncellemesi yapar.ekleme ve güncelleme yapar

    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);//eski görevi çekiyorsun sonra update ile postman den
        // gelen yeni bilgilerle eski bilgileri değiştiriyorum.

        if (optionalTask.isPresent()) {//görevin bulunup bulunmadığını kontrol eder.
            //existingtask zaten varolan veridir.bu kodlarda eski veri yeni
            //veriyle yer değiştirir.
            Task existingTask = optionalTask.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setCompleted(updatedTask.isCompleted());//görevin tamamlamıp tamamlanmadığı bilgisini kontrol
            //eder
            return taskRepository.save(existingTask);//değişiklik yapılan nesne tekrar veritabanına gönderilir
        }

        return null;
    }

    public boolean deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
            return true;
        }

        return false;
    }
}