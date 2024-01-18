package tavebalak.OTTify.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.BadRequestException;
import tavebalak.OTTify.error.exception.InternalServerErrorException;

@Service
@RequiredArgsConstructor
public class AWSS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String url;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile multipartFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + ".jpg";
        if (multipartFile.isEmpty()) {
            throw new BadRequestException(ErrorCode.PROFILE_PHOTO_ISNOT_EXIST);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayInputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new InternalServerErrorException(ErrorCode.UPLOAD_FAILED);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void delete(String fileRoute) {
        int index = fileRoute.indexOf(url);
        String fileName = fileRoute.substring(index + url.length() + 1);

        try {
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, fileName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, fileName);
            }
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorCode.FILE_DELETE_FAILED);
        }
    }
}
