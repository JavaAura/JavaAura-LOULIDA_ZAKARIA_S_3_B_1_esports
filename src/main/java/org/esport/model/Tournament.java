package org.esport.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.esport.model.enums.TournamentStatus;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Min(value = 0)
    private int numberOfSpectators;

    @ManyToMany
    @JoinTable(name = "tournament_team", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teams = new ArrayList<>();

    @Column(nullable = false)
    private int estimatedDuration = 0;

    @Column(nullable = false)
    private int averageMatchDuration = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournamentStatus status = TournamentStatus.PLANNED;

    @Column(nullable = false)
    private int ceremonyTime = 0;

    @Column(nullable = false)
    private int timeBetweenMatches = 0;

    // Constructors
    public Tournament() {
    }

    public Tournament(String title, Game game, LocalDate startDate, LocalDate endDate, int averageMatchDuration) {
        this.title = title;
        this.game = game;
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageMatchDuration = averageMatchDuration;
        this.status = TournamentStatus.PLANNED;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfSpectators() {
        return numberOfSpectators;
    }

    public void setNumberOfSpectators(int numberOfSpectators) {
        this.numberOfSpectators = numberOfSpectators;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getTimeBetweenMatches() {
        return timeBetweenMatches;
    }

    public void setTimeBetweenMatches(int timeBetweenMatches) {
        this.timeBetweenMatches = timeBetweenMatches;
    }

    public int getCeremonyTime() {
        return ceremonyTime;
    }

    public void setCeremonyTime(int ceremonyTime) {
        this.ceremonyTime = ceremonyTime;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public int calculateEstimatedDuration() {
        int numberOfTeams = this.teams.size();
        int gameDifficulty = this.game.getDifficulty();
        int baseEstimation = (numberOfTeams * this.averageMatchDuration * gameDifficulty) + this.timeBetweenMatches;
        return baseEstimation + this.ceremonyTime;
    }

    public int getAverageMatchDuration() {
        return averageMatchDuration;
    }

    public void setAverageMatchDuration(int averageMatchDuration) {
        this.averageMatchDuration = averageMatchDuration;
    }

}
