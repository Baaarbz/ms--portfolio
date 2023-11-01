package dev.eduardobarbosa.portfolio

import com.github.javafaker.Faker
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.JobDTO
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.JobDataDTO
import java.util.UUID

object JobFactory {

    private val faker = Faker.instance()
    fun aJobDTO(): JobDTO {
        val fakerCompany = faker.company();

        return JobDTO(
            id = UUID.randomUUID().toString(),
            companyName = fakerCompany.name(),
            companyURL = fakerCompany.url(),
            companyStartMonth = faker.number().numberBetween(10, 12).toString(),
            companyStartYear = faker.number().numberBetween(1990, 2040).toString(),
            companyEndMonth = null,
            companyEndYear = null,
            isCurrentCompany = true,
            jobData = aJobDataDto()
        )
    }

    private fun aJobDataDto(): JobDataDTO {
        val fakerJob = faker.job()

        return JobDataDTO(
            positions = listOf(
                JobDataDTO.PositionDTO(
                    position = fakerJob.title(),
                    description = fakerJob.position(),
                    isCurrentPosition = true,
                    positionStartMonth = "06",
                    positionStartYear = "2022",
                    positionEndMonth = null,
                    positionEndYear = null,
                )
            )
        )
    }
}