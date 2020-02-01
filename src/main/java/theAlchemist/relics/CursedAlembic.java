package theAlchemist.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import theAlchemist.AlchemistMod;
import theAlchemist.ui.campfire.AlchemizeOption;
import theAlchemist.util.TextureLoader;

import java.util.ArrayList;

import static theAlchemist.AlchemistMod.*;

public class CursedAlembic extends CustomRelic
{
	public static final String ID = AlchemistMod.makeID("CursedAlembic");
	
	private static final Texture IMAGE = TextureLoader.getTexture(makeRelicPath("cursed_alembic.png"));
	private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("cursed_alembic.png"));
	
	public CursedAlembic()
	{
		super(ID, IMAGE, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
	}
	
	@Override
	public boolean canUseCampfireOption(AbstractCampfireOption option)
	{// Literally copy-pasted from the Coffee Dripper - Jolkert 2020-02-01
		if (option instanceof RestOption && option.getClass().getName().equals(RestOption.class.getName()))
		{
			((RestOption)option).updateUsability(false);
			return false;
		}
		else
			return true;
	}
	
	@Override
	public void addCampfireOption(ArrayList<AbstractCampfireOption> options)
	{
		options.add(new AlchemizeOption());
	}
	
	@Override
	public String getUpdatedDescription()
	{
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy()
	{
		return new CursedAlembic();
	}
}
