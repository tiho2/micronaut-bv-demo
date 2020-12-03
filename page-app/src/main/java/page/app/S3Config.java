package page.app;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.micronaut.context.annotation.Property;

import javax.inject.Singleton;

@Singleton
public class S3Config {
    @Property(name = "application.aws.access_key_id")
    private String awsId;

    @Property(name = "application.aws.secret_access_key")
    private String awsKey;

    @Property(name = "application.s3.region")
    private String region;

    @Property(name="application.s3.bucket}")
    private String bucketName;


    public AmazonS3 s3client() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        return s3Client;
    }
}

