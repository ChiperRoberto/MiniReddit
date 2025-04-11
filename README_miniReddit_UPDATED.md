
## ✅ Testare

Aplicația a fost testată utilizând **JUnit 5** și **Mockito**, concentrându-se pe serviciile cheie (`ForumService`, `PostService`, `CommentService`, `ReactionService`). Fiecare serviciu este testat izolat cu ajutorul depependentelor mockuite, verificând:

- Salvarea și recuperarea datelor
- Căutările personalizate în baza de date
- Ștergerea entităților
- Logica specifică de update sau filtrare (ex: reacții)

Exemple:
```java
@Test
void testCreateForum() {
    Forum forum = new Forum("Java", new User());
    when(forumRepository.save(forum)).thenReturn(forum);
    Forum result = forumService.createForum(forum);
    assertEquals("Java", result.getName());
}
```

```java
@Test
void testCountReactions() {
    Post post = new Post();
    Reaction r1 = new Reaction(); r1.setType(ReactionType.LIKE);
    Reaction r2 = new Reaction(); r2.setType(ReactionType.LIKE);
    Reaction r3 = new Reaction(); r3.setType(ReactionType.SAD);
    when(reactionRepository.findByPost(post)).thenReturn(Arrays.asList(r1, r2, r3));
    long likeCount = reactionService.countReactions(post, ReactionType.LIKE);
    assertEquals(2, likeCount);
}
```

Toate testele pot fi rulate cu `mvn test` sau direct din IDE (ex: IntelliJ IDEA).
