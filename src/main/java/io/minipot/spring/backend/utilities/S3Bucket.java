package io.minipot.spring.backend.utilities;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import java.util.List;

@Component
public class S3Bucket {

    private S3Client s3Client;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.default.name}")
    private String defaultBucketName;

    @PostConstruct
    public void init(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();

        initApplicationBucket(defaultBucketName);
    }

    public void initApplicationBucket (String bucketName){
        List <Bucket> allBuckets = s3Client.listBuckets().buckets();
        String bucket = bucketName.toLowerCase();

        for (Bucket b : allBuckets){
            if (b.name().equals(bucket)){
                System.out.println("S3 Bucket already existing.");
                return;
            }
        }
        s3Client.createBucket(request -> request.bucket(bucket));
        System.out.println("Bucket " + bucketName + " created!");
    }
}
