import object_model.GetListUsersModel;
import org.testng.annotations.Test;

public class GetListUsers {
    @Test
    public void getUsersInTheFirstPage() {
        new GetListUsersModel()
                .getUsersInPage(1)
                .assertNumOfUsersPerPage(6)
                .assertNthUserEmail(1, "janet.weaver@reqres.in");
    }
}
