package page.app;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Property;
import page.app.domain.Status;

import javax.inject.Singleton;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Singleton
public class BookService {

    @Property(name = "aws.access_key_id")
    private String awsId;

    @Property(name = "aws.secret_access_key")
    private String awsKey;

    @Property(name = "aws.s3.region")
    private String region;

    @Property(name = "aws.s3.bucket")
    private String bucketName;

    private StatusUpdater statusUpdater;

//    final private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//            .withRegion(Regions.fromName(region))
//            .withCredentials(new AWSStaticCredentialsProvider(
//                    new BasicAWSCredentials(awsId, awsKey)))
//            .build();

// S3 client connection test


    public BookService(StatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    /**
     * TODO: used as initial connection test in learning phase - application doesn't need that functionality
     */
    public void outputBuckets() {

        Bucket named_bucket = null;
        List<Bucket> buckets = s3Client().listBuckets();
        for (Bucket b : buckets) {
            System.out.format("bucket. %s", b.getName());
            System.out.println(b.getName());
        }
    }

    public void storeObject(String bucketName, String keyName, InputStream is, ObjectMetadata objectMetadata) {

        s3Client().putObject(new PutObjectRequest(bucketName, keyName, is, objectMetadata));

    }

    public String createPresignedUrl(String bucketName, String objectKey) {
        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 48;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        System.out.println("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client().generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();

    }

    public void statusUpdate(Status status) {
        this.statusUpdater.update(status);
    }

    public String getAwsId() {
        return awsId;
    }

    public void setAwsId(String awsId) {
        this.awsId = awsId;
    }

    public String getAwsKey() {
        return awsKey;
    }

    public void setAwsKey(String awsKey) {
        this.awsKey = awsKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Bean
    AmazonS3 s3Client() {

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region)).withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsId, awsKey)))
                .build();
    }
}
