package winside.gmonnet.tondeuse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import winside.gmonnet.tondeuse.entites.Route;
import winside.gmonnet.tondeuse.entites.Tondeuse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class BatchConfiguration {
    @Value("${tondeuse.outputFolder}")
    private String dossierSortie;

    @Bean
    public FlatFileItemWriter<Tondeuse> writer() throws IOException {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path fichierSortie = Files.createFile(Paths.get(dossierSortie, dateTime + ".txt"));

        return new FlatFileItemWriterBuilder<Tondeuse>()
                .name("TondeuseWriter")
                .lineAggregator(new PassThroughLineAggregator<>())
                .resource(new FileSystemResource(fichierSortie))
                .build();
    }

    @Bean
    public Job tondrePelouseJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("tondrePelouseJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step stepTondeuse(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemProcessor<Route, Tondeuse> processor, ItemReader<Route> reader) throws URISyntaxException, IOException {
        return new StepBuilder("stepTondeuse", jobRepository)
                .<Route, Tondeuse> chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer())
                .build();
    }
}
