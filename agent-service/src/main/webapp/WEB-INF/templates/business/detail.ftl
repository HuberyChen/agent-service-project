<@master template="layout/master">
<section class="main">
    <form id="detailForm" action="<@url value='/business'/>" class="form-signin" method="post">
        <input type="text" name="detailViews[0].sku"/>
        <input type="text" name="detailViews[1].sku"/>

        <p>
            <button type="submit">submit</button>
        </p>
    </form>
</section>

</@master>