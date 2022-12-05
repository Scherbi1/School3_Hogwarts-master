package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

private static final Logger LOG = LoggerFactory.getLogger(AvatarService.class );
private final StudentService studentService;

private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        LOG.debug("Method uploadAvatar was invoked");
        Student student = studentService.getStudentById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }
    private String getExtensions(String fileName) {
        LOG.debug("Method getExtensions was invoked");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar (Long studentId) {
        LOG.debug("Method findAvatar was invoked");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public ResponseEntity<List<Avatar>> findAvatarAll(Integer pageNumber, Integer pageSize) {
        LOG.debug("Method findAvatarAll was invoked");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Avatar> avatar =avatarRepository.findAll((pageRequest)).getContent();
          return ResponseEntity.ok(avatar);
    }
}

